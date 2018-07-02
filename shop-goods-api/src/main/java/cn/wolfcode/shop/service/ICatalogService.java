package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.Catalog;

import java.util.List;

/**
 * Created by wolfcode on 2018/03/29 0029.
 */
public interface ICatalogService {

    /**
     * 获取所有的分类
     */
    List<Catalog> getAllCatalog();

    /**
     * 通过商品分类id获取子分类
     * @param catalogId
     * @return
     */
    List<Catalog> getChildCatalog(Long catalogId);

    /**
     * 更新分类排序
     * @param ids
     */
    void updateSort(List<Long> ids);

    /**
     * 编辑和修改分类
     * @param catalog
     */
    void save(Catalog catalog);

    /**
     * 删除分类
     * @param catalogId
     */
    void delete(Long catalogId);

    /**
     * 从Redis中获取商品分类树结构的缓存
     * @return
     */
    String getCatalogRedisCache();

    /**
     * 统计分类下的商品个数
     */
    Integer countProduct(Long catalogId);

    /**
     * 统计分类下的属性个数
     */
    Integer countProperty(Long catalogId);
}






