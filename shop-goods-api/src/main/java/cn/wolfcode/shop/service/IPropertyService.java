package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.Property;

import java.util.List;

/**
 * Created by wolfcode on 2018/03/31 0031.
 */
public interface IPropertyService {

    /**
     *通过分类id获取该分类下的商品属性
     */
    List<Property> getProByCatalogId(Long catalogId);

    /**
     * 修改或新增商品属性
     * @param property
     */
    void save(Property property);

    /**
     * 通过商品属性id删除该属性
     * @param id
     */
    void delete(Long id);
}
