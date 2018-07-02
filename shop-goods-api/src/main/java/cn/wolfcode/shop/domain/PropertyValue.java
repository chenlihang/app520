package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class PropertyValue extends  BaseDomain{

    private Long propertyId;

    private String value;


}