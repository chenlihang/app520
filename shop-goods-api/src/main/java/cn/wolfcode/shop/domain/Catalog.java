package cn.wolfcode.shop.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Catalog extends BaseDomain{


    private static final long serialVersionUID = 1L;

    private String name;

    private String code;

    private Integer sort;

    private Long pId;

    private Byte isParent;

    private Integer countProduct;

    private Integer countProperty;


    public String getJsonData(){
        JSONObject map = new JSONObject();
        map.put("id",this.getId());
        map.put("name",this.name);
        map.put("code",this.code);
        map.put("sort",this.sort);
        map.put("pId",this.pId);
        map.put("isParent",this.isParent);
        return JSON.toJSONString(map);
    }

}