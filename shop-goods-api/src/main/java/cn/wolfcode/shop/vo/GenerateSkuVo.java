package cn.wolfcode.shop.vo;

import cn.wolfcode.shop.domain.Product;
import cn.wolfcode.shop.domain.SkuProperty;
import cn.wolfcode.shop.domain.SkuPropertyValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wolfcode on 2018/04/01 0001.
 */
@Setter@Getter@ToString
public class GenerateSkuVo implements Serializable{

    private Product product;

    private List<SkuProperty> skuPropertyList;

    private List<SkuPropertyValue> skuPropertyValueList;


}
