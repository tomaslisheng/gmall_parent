package com.atguigu.gmall.client;

import com.atguigu.gmall.product.CategoryView;
import com.atguigu.gmall.product.SkuImage;
import com.atguigu.gmall.product.SkuInfo;
import com.atguigu.gmall.product.SpuSale;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * author lisheng
 * Date:2021/5/19
 * Description:
 */
@FeignClient("service-item")
public interface ItemFeignClient {
    @RequestMapping("item/getProductDetail/{skuId}")
    public Map<String, Object> getProductDetail(@PathVariable Long skuId);
    @RequestMapping("item/getProduct/{skuId}")
    SkuInfo getProduct(@PathVariable Long skuId);
    @RequestMapping("item/getCategoryView/{skuId}")
    CategoryView getCategoryView(@PathVariable Long skuId);
    @RequestMapping("item/getSkuImageList/{skuId}")
    List<SkuImage> getSkuImageList(@PathVariable Long skuId);
    @RequestMapping("item/getSaleProductId/{spuId}/{skuId}")
    List<SpuSale> getSaleProductId(@PathVariable Long spuId,@PathVariable Long skuId);
    @RequestMapping("item/getSkuBySpuId/{spuId}")
    String getSkuBySpuId(@PathVariable Long spuId);
}
