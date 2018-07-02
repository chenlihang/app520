package cn.wolfcode.shop.scheduler;

import cn.wolfcode.shop.constants.RedisConstants;
import cn.wolfcode.shop.domain.Catalog;
import cn.wolfcode.shop.service.ICatalogService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wolfcode on 2018/03/31 0031.
 */
@Component
public class CatalogCountScheduler {

    @Reference
    ICatalogService catalogService;


    @Autowired
    RedisTemplate redisTemplate;
    /**
     * 定时执行更新Redis中分类的统计(商品和属性)
     * 每天凌晨3点执行该定时
     */
    @Scheduled(cron = "0 11 11 * * ?")
    public void countCatalog(){
        List<Catalog> allCatalog = catalogService.getAllCatalog();
        allCatalog.forEach(catalog ->{
            Integer countProduct = catalogService.countProduct(catalog.getId());
            Integer countProperty = catalogService.countProperty(catalog.getId());
            redisTemplate.opsForValue().set(RedisConstants.CATALOG_ID_COUNTPRODUCT.replaceAll("id", catalog.getId() + ""),countProduct);
            redisTemplate.opsForValue().set(RedisConstants.CATALOG_ID_COUNTPROPERTY.replaceAll("id", catalog.getId() + ""),countProperty);
        });
        System.out.println("定时执行更新Redis中分类的统计");
    }
}
