package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.Product;
import cn.wolfcode.shop.domain.ProductSku;
import cn.wolfcode.shop.domain.SkuProperty;
import cn.wolfcode.shop.domain.SkuPropertyValue;
import cn.wolfcode.shop.service.IProductService;
import cn.wolfcode.shop.service.IProductSkuService;
import cn.wolfcode.shop.service.ISkuPropertyService;
import cn.wolfcode.shop.service.ISkuPropertyValueService;
import cn.wolfcode.shop.vo.GenerateSkuVo;
import cn.wolfcode.shop.vo.JSONResultVo;
import cn.wolfcode.shop.vo.ProductVo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by wolfcode on 2018/04/01 0001.
 */
@Controller
@RequestMapping("/productSku")
public class ProductSkuController {

    @Reference
    IProductService productService;
    @Reference
    ISkuPropertyService skuPropertyService;

    @Reference
    ISkuPropertyValueService skuPropertyValueService;
    @Reference
    IProductSkuService productSkuService;

    @RequestMapping(value = "")
    public String method(Long productId,Map map){

        //通过商品id获取整个商品对象
        Product product = productService.getProductById(productId);
        map.put("product",product);

        //通过该商品获取商品下的sku数据
        List<ProductSku> skuList = productSkuService.getSkuByPid(product.getId());
        //判断该商品是否有sku数据
        if (skuList != null && skuList.size() > 0){
            map.put("skuList",skuList);
            //如果有生成sku,就跳转到sku管理界面
            return "product_sku/sku_list";

        }
         //否则,还是跳转到原来的sku生成界面
        //通过该商品的所属分类,获取分类下的sku属性
        List<SkuProperty> skuPropertyList = skuPropertyService.getProByCatalogId(product.getCatalog().getId());




        map.put("skuPropertyList",skuPropertyList);
        return "product_sku/generate_sku";
    }

    /**
     * 通过sku属性id获取属性下的属性值集合
     * @param skuPropertyId
     * @return
     */
    @RequestMapping(value = "/getSkuPropertyValue")
    public String getSkuPropertyValue(Long skuPropertyId,Map map){
        SkuProperty skuProperty = skuPropertyService.getById(skuPropertyId);
        List<SkuPropertyValue> skuPropertyValueList = skuPropertyValueService.getProValByProId(skuPropertyId);
        map.put("skuPropertyValueList",skuPropertyValueList);
        map.put("skuProperty",skuProperty);
        return "product_sku/sku_property_value_table";

    }

    @RequestMapping(value = "/generateSku")
    public String generateSku(@RequestBody GenerateSkuVo vo,Map map){
       // productSkuService.getnerateSku(vo);
        List<Map<String, Object>> skuList = productSkuService.generateSku(vo);
        map.put("skuList",skuList);
        map.put("skuProList",vo.getSkuPropertyList());

        return "product_sku/sku_form";
    }
    @RequestMapping(value = "/save")
    @ResponseBody
    public JSONResultVo save(ProductVo vo){
        JSONResultVo jsonResultVo = new JSONResultVo();
        try {
            productSkuService.save(vo);

        }catch (Exception e){
            e.printStackTrace();
            jsonResultVo.setErrorMsg(e.getMessage());

        }
        return jsonResultVo;
    }


}
