package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.Property;
import cn.wolfcode.shop.service.ICatalogService;
import cn.wolfcode.shop.service.IPropertyService;
import cn.wolfcode.shop.vo.JSONResultVo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by wolfcode on 2018/03/31 0031.
 */
@Controller
@RequestMapping(value = "/property")
public class PropertyController {

    @Reference
    ICatalogService catalogService;

    @Reference
    IPropertyService propertyService;

    /**
     * 商品属性管理界面
     */
    @RequestMapping(value = "")
    public String property(Map map){
        map.put("allCatalog",catalogService.getCatalogRedisCache());
        return "property/property";
    }

    /**
     * 通过分类的id获取该分类下的商品属性
     */
    @RequestMapping("/get/{catalogId}")
    public String getProByCatalogId(@PathVariable("catalogId") Long catalogId, Map map){
        List<Property> propertyList = propertyService.getProByCatalogId(catalogId);
        map.put("propertyList",propertyList);
        return "property/property_list";
    }

    /**
     * 修改和新增商品属性
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public JSONResultVo save(Property property){

        JSONResultVo jsonResultVo = new JSONResultVo();
        try {

            propertyService.save(property);
        } catch (Exception e){
            e.printStackTrace();
            jsonResultVo.setErrorMsg(e.getMessage());
        }
        return jsonResultVo;
    }

    /**
     * 通过商品属性id删除该属性
     */
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public JSONResultVo method(@PathVariable("id") Long id){

        JSONResultVo jsonResultVo = new JSONResultVo();
        try {

            propertyService.delete(id);
        } catch (Exception e){
            e.printStackTrace();
            jsonResultVo.setErrorMsg(e.getMessage());
        }
        return jsonResultVo;
    }
}














