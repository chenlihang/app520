package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.*;
import cn.wolfcode.shop.mapper.CatalogMapper;
import cn.wolfcode.shop.mapper.ProductSkuMapper;
import cn.wolfcode.shop.mapper.ProductSkuPropertyMapper;
import cn.wolfcode.shop.service.IProductSkuService;
import cn.wolfcode.shop.vo.GenerateSkuVo;
import cn.wolfcode.shop.vo.ProductVo;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class ProductSkuServiceImpl implements IProductSkuService {
    @Autowired
    CatalogMapper catalogMapper;
    @Autowired
    ProductSkuMapper productSkuMapper;
    @Autowired
    ProductSkuPropertyMapper productSkuPropertyMapper;
    @Override
    public List<Map<String, Object>> generateSku(GenerateSkuVo vo) {
        Product product = vo.getProduct();
        List<SkuProperty> skuPropertyList = vo.getSkuPropertyList();
        List<SkuPropertyValue> skuPropertyValueList = vo.getSkuPropertyValueList();

        //生成sku编码前缀
        List<Catalog> catalogList = catalogMapper.getParentCatalogList(product.getCatalog().getId());
        //生成出来的编码前缀(2位商品一级分类的缩写+2位商品二级分类顺序+2位商品三级分类顺序+商品Id)
        String codePrefix = getCodePrefix(catalogList) + product.getId();
        //用map对数据将行分类,key为属性id,value为该属性下的属性值:Map<String,List<String>>
        Map<String,List<String>> mapList =  getMapList(skuPropertyList,skuPropertyValueList);
        //把map数据转成List<List<String>>.准备递归
        List<List<String>> recursiveList = new ArrayList<>();
        skuPropertyList.forEach(skuPro ->{
            recursiveList.add(mapList.get(skuPro.getId()+""));
        });
        //进行递归算法,最后递归出来的结果为List<list<String>>
        List<List<String>> resultList = new ArrayList<>();
        recursive(resultList,recursiveList,0,new ArrayList<String>());
        //最后把List数据转成map到前台显示List<Map<StringObject>>
        List<Map<String,Object>> skuList = new ArrayList<>();
        //遍历刚刚递归出的最终sku组合的集合
        for (int i=0;i<recursiveList.size();i++){
            //创建map,每个Map代表一个sku数据
            Map<String, Object> sku = new HashMap<>();
            sku.put("code",codePrefix+(i+1));
            sku.put("price",product.getBasePrice());
            //遍历skuPropertyList,在循环中添加xky属性
            for (int j = 0;j<skuPropertyList.size();j++){
                sku.put(""+skuPropertyList.get(j).getId(),recursiveList.get(i).get(j));
            }
            skuList.add(sku);
        }
        return skuList;
    }

    @Override
    public void save(ProductVo vo) {
        //取出商品sku集合
        List<ProductSku> productSkuList = vo.getProductSkuList();
        //遍历sku集合
        productSkuList.forEach(sku ->{
            //给sku设置商品id
            sku.setProductId(vo.getProduct().getId());
            //插入sku数据,生成增长的id
            productSkuMapper.insert(sku);
            //获取该sku中的sku属性集合
            List<ProductSkuProperty> productSkuPropertyList = sku.getProductSkuPropertyList();
            //遍历sku属性集合
            productSkuPropertyList.forEach(skuPro -> {
                //没遍历sku属性集合
                skuPro.setProductSkuId(sku.getId());
                //插入sku属性
                productSkuPropertyMapper.insert(skuPro);
            });
        });

    }

    @Override
    public List<ProductSku> getSkuByPid(Long productId) {
        return productSkuMapper.getSkuByPid(productId);
    }

    /**
     * 生成sku编码前缀
     * @param catalogList
     * @return
     */
    private String getCodePrefix(List<Catalog> catalogList) {
        StringBuilder sb = new StringBuilder();
        //去掉根分类
        catalogList.remove(0);
        //遍历该集合
        for(int i = 0;i<catalogList.size();i++){
            //是否为一级分类
            if(i == 0){
                //如果是一级分类,就获取分类编号的前两位
                String catalogCode = catalogList.get(i).getCode();
                sb.append(catalogCode.length() > 2 ? catalogCode.substring(0,2) : catalogCode);
            }else{
                //否则
                //获取其他级别分类的排序号
                Integer catalogSort = catalogList.get(i).getSort();
                sb.append(catalogSort > 9 ? catalogSort + "" : "0" + catalogSort);
            }
        }


        return sb.toString();

    }

    /**
     * 生成map<String,List<String>>
     * @param skuPropertyList
     * @param skuPropertyValueList
     * @return
     */
    private Map<String,List<String>> getMapList(List<SkuProperty> skuPropertyList, List<SkuPropertyValue> skuPropertyValueList) {
        Map<String, List<String>> mapList = new HashMap<>();

        //遍历skuPropertyList集合
        skuPropertyList.forEach(skuPro -> {
            //创建一个空的List集合,用skuPropertyId作为key,该空List作为value
            mapList.put(skuPro.getId()+"",new ArrayList<>());
            //遍历skuPropertyValueList集合
            skuPropertyValueList.forEach(skuProVal -> {
                //判断当前外循环的skupropertyId是否等于内循环中遍历的skuPropertyValue的skuPropertyId
                if(skuPro.getId() == skuProVal.getSkuPropertyId()){
                    //如果是相等的,就把该skuPropertyValue的值添加到skuPropertyList集合中
                    mapList.get(skuPro.getId()+"").add(skuProVal.getValue());
                }
            });
        });
        return mapList;
    }


    /**
     * 递归生成sku
     * @param resultList 最终的sku数据集合
     * @param recursiveList 用于递归的基础数据
     * @param layer 递归的层级
     * @param skuData 存放sku组合的集合
     */
    private void recursive(List<List<String>> resultList, List<List<String>> recursiveList, int layer, List<String> skuData) {
        //判断递归的层级是否为最后一层
        if (recursiveList.size() - 1 > layer) {
            //不是最后一层
            //获取该层级的List,并遍历
            recursiveList.get(layer).forEach(str -> {
                //获取遍历每个list元素,往传入的skuData集合追加进去

                List<String> tempList = new ArrayList(skuData);
                tempList.add(str);
                //递归
                recursive(resultList, recursiveList, layer + 1, tempList);
            });
        } else {
            //是最后一层
            //获取该层级的List,并遍历
            recursiveList.get(layer).forEach(str -> {
                //获取遍历每一个list元素,往传入的skuData集合中加进去
                List<String> tempList = new ArrayList(skuData);
                tempList.add(str);
                //把一个sku完整的组合添加到最终的存放sku的集合中
                resultList.add(tempList);

            });


        }

    }
    }
