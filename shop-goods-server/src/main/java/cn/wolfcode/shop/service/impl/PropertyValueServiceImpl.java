package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.PropertyValue;
import cn.wolfcode.shop.mapper.PropertyValueMapper;
import cn.wolfcode.shop.service.IPropertyService;
import cn.wolfcode.shop.service.IPropertyValueService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wolfcode on 2018/03/31 0031.
 */
@Service
@Transactional
public class PropertyValueServiceImpl implements IPropertyValueService {

    @Autowired
    PropertyValueMapper propertyValueMapper;

    @Override
    public List<PropertyValue> getProValByProId(Long propertyId) {
        return propertyValueMapper.getProValByProId(propertyId);
    }

    @Override
    public void save(List<PropertyValue> proValList) {
        //变量属性值集合
        proValList.forEach(proVal -> {
            //获取每个属性值的id
            //如果id不为nul,则执行更新的操作
            if(proVal.getId() != null){
                propertyValueMapper.updateByPrimaryKey(proVal);
            }else{
                //否则,执行插入的操作
                propertyValueMapper.insert(proVal);
            }
        });

    }

    @Override
    public void delete(Long id) {
        propertyValueMapper.deleteByPrimaryKey(id);
    }
}











