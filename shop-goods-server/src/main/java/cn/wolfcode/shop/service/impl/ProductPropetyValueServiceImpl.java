package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.ProductPropertyValue;
import cn.wolfcode.shop.mapper.ProductPropertyValueMapper;
import cn.wolfcode.shop.service.IProductPropertyValueService;
import cn.wolfcode.shop.service.IPropertyValueService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wolfcode on 2018/04/01 0001.
 */
@Service
@Transactional
public class ProductPropetyValueServiceImpl implements IProductPropertyValueService {

    @Autowired
    ProductPropertyValueMapper productPropertyValueMapper;

    @Override
    public void save(ProductPropertyValue productPropertyValue) {
        productPropertyValueMapper.insert(productPropertyValue);
    }
}
