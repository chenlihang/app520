package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.PropertyValue;
import cn.wolfcode.shop.domain.SkuPropertyValue;

import java.util.List;

/**
 * Created by wolfcode on 2018/03/31 0031.
 */
public interface ISkuPropertyValueService {

    /**
     * 通过属性id获取属性值
     */
    public List<SkuPropertyValue> getProValByProId(Long PropertyId);

    /**
     * 批量新增或修改属性值
     * @param proValList
     */
    void save(List<SkuPropertyValue> proValList);

    /**
     * 通过属性值id删除对应的属性值
     * @param id
     */
    void delete(Long id);
}
