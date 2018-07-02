package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.SkuProperty;

import java.util.List;

/**
 * Created by wolfcode on 2018/03/31 0031.
 */
public interface ISkuPropertyService {

    /**
     *通过分类id获取该分类下的商品属性
     */
    List<SkuProperty> getProByCatalogId(Long catalogId);

    /**
     * 修改或新增商品属性
     * @param SkuProperty
     */
    void save(SkuProperty skuProperty);

    /**
     * 通过商品属性id删除该属性
     * @param id
     */
    void delete(Long id);

    /**
     * 通过sku属性id获取sku属性对象
     * @param skuPropertyId
     * @return
     */
    SkuProperty getById(Long skuPropertyId);
}
