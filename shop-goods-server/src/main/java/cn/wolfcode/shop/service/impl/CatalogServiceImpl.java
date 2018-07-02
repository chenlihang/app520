package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.constants.RedisConstants;
import cn.wolfcode.shop.domain.Catalog;
import cn.wolfcode.shop.mapper.CatalogMapper;
import cn.wolfcode.shop.service.ICatalogService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wolfcode on 2018/03/29 0029.
 */
@Service
@Transactional
public class CatalogServiceImpl implements ICatalogService{

    @Autowired
    CatalogMapper catalogMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<Catalog> getAllCatalog() {
        return catalogMapper.selectAll();
    }

    @Override
    public List<Catalog> getChildCatalog(Long catalogId) {

        //通过分类id获取子分类
        List<Catalog> childCatalog = catalogMapper.getChildCatalog(catalogId);
        //遍历子分类集合
        childCatalog.forEach(catalog -> {
            //从Redis中获取当前遍历的子分类的统计(包含商品和属性)
            Integer countProduct = (Integer) redisTemplate.opsForValue().
                    get(RedisConstants.CATALOG_ID_COUNTPRODUCT.replaceAll("id", catalog.getId() + ""));
            Integer countProperty = (Integer) redisTemplate.opsForValue().
                    get(RedisConstants.CATALOG_ID_COUNTPROPERTY.replaceAll("id", catalog.getId() + ""));
            //如果商品和属性的统计数为null
            if(countProduct == null){
                //从数据库重新统计商品和属性个数,并缓存到Redis中
                countProduct= countProduct(catalog.getId());
                countProperty= countProperty(catalog.getId());
                redisTemplate.opsForValue().set(RedisConstants.CATALOG_ID_COUNTPRODUCT.
                        replaceAll("id", catalog.getId() + ""),countProduct);
                redisTemplate.opsForValue().set(RedisConstants.CATALOG_ID_COUNTPROPERTY.
                        replaceAll("id", catalog.getId() + ""),countProperty);
            }

            //把商品和属性的统计数设置到当前遍历到的分类中
            catalog.setCountProduct(countProduct);
            catalog.setCountProperty(countProperty);
        });

        return childCatalog;
    }

    @Override
    public void updateSort(List<Long> ids) {
        //变历ids的list集合
        for(int i = 0;i<ids.size();i++){
            //通过id获取对应的分类对象
            Catalog catalog = catalogMapper.selectByPrimaryKey(ids.get(i));
            //设置该分类对象的排序,把list的索引设置到排序属性中
            catalog.setSort(i+1);
            //更新分类
            catalogMapper.updateByPrimaryKey(catalog);
        }
        //更新存在Redis中的商品分类树缓存
        refleshRedisCatalog();
    }

    @Override
    public void save(Catalog catalog) {
        //通过分类id判断是新增还是编辑
        if(catalog.getId() == null){
            //如果是新增的话,获取父分类
            Catalog parentCatalog = catalogMapper.selectByPrimaryKey(catalog.getPId());

            if(parentCatalog.getIsParent() == 0){
                //判断父分类isParent是否为false,否则就不需要操作
                parentCatalog.setIsParent(new Byte("1"));
                //如果为false,就这种为true,并且更新父分类
                catalogMapper.updateByPrimaryKey(parentCatalog);
            }
            //执行新增操作
            catalogMapper.insert(catalog);
        }else{
            //否则,就执行编辑操作
            catalogMapper.updateByPrimaryKey(catalog);
        }
        //更新存在Redis中的商品分类树缓存
        refleshRedisCatalog();

    }

    @Override
    public void delete(Long catalogId) {
        //通过分类id获取整个分类对象
        Catalog catalog = catalogMapper.selectByPrimaryKey(catalogId);
        //通过当前需要删除的分类对象获取父分类id
        Long pId = catalog.getPId();
        //最后删除当前的分类
        catalogMapper.deleteByPrimaryKey(catalogId);
        //通过父分类id获取父分类对象
        Catalog parentCatalog = catalogMapper.selectByPrimaryKey(pId);
        //判断父分类对象是否还有值分类
        List<Catalog> childCatalog = catalogMapper.getChildCatalog(pId);
        if(childCatalog.size() == 0){
            //如果没有没有子分类,就把父分类的isParent设置为false
            parentCatalog.setIsParent(new Byte("0"));
            //更新父分类
            catalogMapper.updateByPrimaryKey(parentCatalog);
        }

        //更新存在Redis中的商品分类树缓存
        refleshRedisCatalog();
    }

    @Override
    public String getCatalogRedisCache() {
        //从Redis中获取商品分类树
        String catalogJson = (String) redisTemplate.opsForValue().get(RedisConstants.CATALOG_ALL);
        //判断数据是否为nul
        if(catalogJson == null){
            catalogJson = refleshRedisCatalog();
        }
        return catalogJson;
    }

    @Override
    public Integer countProduct(Long catalogId) {
        return catalogMapper.countProduct(catalogId);
    }

    @Override
    public Integer countProperty(Long catalogId) {
        return catalogMapper.countProperty(catalogId);
    }

    private String refleshRedisCatalog() {
        String catalogJson;//如果为nul,则去数据库重新获取整课商品分类树
        List<Catalog> catalogs = catalogMapper.selectAll();
        catalogJson = JSON.toJSONString(catalogs);
        //缓存到Redis中
        redisTemplate.opsForValue().set(RedisConstants.CATALOG_ALL,catalogJson);
        return catalogJson;
    }
}










