package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.ProductSku;
import cn.wolfcode.shop.vo.GenerateSkuVo;
import cn.wolfcode.shop.vo.ProductVo;

import java.util.List;
import java.util.Map;

public interface IProductSkuService {
    /**
     * 生成sku
     * @param vo
     * @return
     */
    List<Map<String,Object>> generateSku(GenerateSkuVo vo);
    /**
     * 保存sku
     */
    void save(ProductVo vo);

    /**
     * 通过该商品获取商品下的sku数据
     *
     * @param productId
     * @return
     */
    List<ProductSku> getSkuByPid(Long productId);
}
