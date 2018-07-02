package cn.wolfcode.shop.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter@Getter
public class Property extends  BaseDomain{

    private static final long serialVersionUID = 4660315984312479859L;

    private Long catalogId;

    private String name;

    private Integer sort;

    private Byte type;

    private List<PropertyValue> propertyValueList;

    public String getJson(){
        JSONObject map = new JSONObject();
        map.put("id",this.getId());
        map.put("catalogId",this.catalogId);
        map.put("name",this.name);
        map.put("sort",this.sort);
        map.put("type",this.type);
        return JSON.toJSONString(map);
    }
}