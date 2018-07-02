package cn.wolfcode.shop.qo;

/**
 * Created by luohaipeng on 2018/3/13.
 */

public class ProductQueryObject extends QueryObject {

    private String keyword;

    public void setKeyword(String keyword){
        if(keyword != null && keyword.trim().length() > 0){
            this.keyword = "%"+keyword+"%";
        }
    }

}
