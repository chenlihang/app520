package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.PropertyValue;
import cn.wolfcode.shop.service.IPropertyValueService;
import cn.wolfcode.shop.vo.JSONResultVo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by wolfcode on 2018/03/31 0031.
 */
@RequestMapping("/propertyValue")
@Controller
public class PropertyValueController {

    @Reference
    IPropertyValueService propertyValueService;

    @RequestMapping(value = "/get/{propertyId}")
    public String propertyValue(@PathVariable("propertyId") Long propertyId, Map map){

        map.put("propertyValueList",propertyValueService.getProValByProId(propertyId));
        return "property/property_value_list";
    }

    /**
     * 批量新增或修改属性值
     * @param proValList
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public JSONResultVo save(@RequestBody List<PropertyValue> proValList){

        JSONResultVo jsonResultVo = new JSONResultVo();
        try {
            propertyValueService.save(proValList);

        } catch (Exception e){
            e.printStackTrace();
            jsonResultVo.setErrorMsg(e.getMessage());
        }
        return jsonResultVo;
    }

    /**
     * 通过属性值id删除对应的属性值
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public JSONResultVo delete(@PathVariable("id") Long id){

        JSONResultVo jsonResultVo = new JSONResultVo();
        try {
            propertyValueService.delete(id);

        } catch (Exception e){
            e.printStackTrace();
            jsonResultVo.setErrorMsg(e.getMessage());
        }
        return jsonResultVo;
    }
}
