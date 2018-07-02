package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.ProductPropertyValue;

/**
 * Created by wolfcode on 2018/04/01 0001.
 */
public interface IProductPropertyValueService {

    /**
     * 保存商品属性
     */
    void save(ProductPropertyValue productPropertyValue);
}
