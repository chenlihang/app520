package cn.wolfcode.shop.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.developer.StreamingAttachment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter@Getter
public class SkuProperty extends BaseDomain{

    private Long catalogId;

    private String name;

    private Byte type;

    private Integer sort;

    private List<SkuPropertyValue> skuPropertyValueList;

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