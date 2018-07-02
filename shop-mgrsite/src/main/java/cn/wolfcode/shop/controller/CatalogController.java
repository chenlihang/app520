package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.Catalog;
import cn.wolfcode.shop.service.ICatalogService;
import cn.wolfcode.shop.vo.JSONResultVo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.awt.SunHints;

import java.util.List;
import java.util.Map;

/**
 * Created by wolfcode on 2018/03/29 0029.
 */
@Controller
@RequestMapping(value = "/catalog")
public class CatalogController {

    @Reference
    ICatalogService catalogService;

    @RequestMapping(value = "/getAllCatalog")
    @ResponseBody
    public List<Catalog> getAllCatalog(){
        return catalogService.getAllCatalog();
    }

    /**
     * 商品分类管理界面
     * @return
     */
    @RequestMapping(value = "")
    public String catalog(Model model){

        //List<Catalog> allCatalog = catalogService.getAllCatalog();
        //map.put("allCatalog",allCatalog);
        String catalogJson = catalogService.getCatalogRedisCache();
        model.addAttribute("allCatalog", catalogJson);

        return "catalog/catalog";
    }

    /**
     * 通过商品分类id获取子分类
     * @param catalogId
     * @return
     */
    @RequestMapping(value = "/getChildCatalog")
    public String getChildCatalog(Long catalogId,Map map){

        List<Catalog> catalogList = catalogService.getChildCatalog(catalogId);
        map.put("catalogList",catalogList);
        return "catalog/child_catalog";
    }

    /**
     * 更新分类排序
     * @param ids
     * @return
     */
    @RequestMapping(value = "/updateSort")
    @ResponseBody
    public JSONResultVo updateSort(@RequestBody List<Long> ids){

        JSONResultVo jsonResultVo = new JSONResultVo();
        try {
            catalogService.updateSort(ids);
        }catch (Exception e){
            e.printStackTrace();
            jsonResultVo.setErrorMsg(e.getMessage());
        }
        return jsonResultVo;
    }

    /**
     * 编辑和修改分类
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo save(Catalog catalog){
        JSONResultVo jsonResultVo = new JSONResultVo();
        try {
            catalogService.save(catalog);
        }catch (Exception e){
            jsonResultVo.setErrorMsg(e.getMessage());
        }
        return jsonResultVo;
    }

    /**
     * 删除分类
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public JSONResultVo deleteCatalog(Long catalogId){
        JSONResultVo jsonResultVo = new JSONResultVo();
        try {
            catalogService.delete(catalogId);
        }catch (Exception e){
            jsonResultVo.setErrorMsg(e.getMessage());
        }
        return jsonResultVo;
    }


}










