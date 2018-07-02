package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;
import sun.misc.BASE64Decoder;

import java.util.Date;

@Setter@Getter
public class Brand extends BaseDomain{

    private String name;

    private Date createdDate;

    private Integer sort;

    private String logo;

    private String description;

    private String url;

}