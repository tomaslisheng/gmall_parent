package com.atguigu.gmall.client;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.list.Goods;
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
@FeignClient("service-product")
public interface ProductFeignClient {

    @RequestMapping("product/getProductDetail/{skuId}")
    public Map<String,Object> getProductDetail(@PathVariable Long skuId);
    @RequestMapping("product/getProduct/{skuId}")
    SkuInfo getProduct(@PathVariable Long skuId);
    @RequestMapping("product/getCategoryView/{skuId}")
    CategoryView getCategoryView(@PathVariable Long skuId);
    @RequestMapping("product/getSkuImageList/{skuId}")
    List<SkuImage> getSkuImageList(@PathVariable Long skuId);
    @RequestMapping("product/getSaleProductId/{spuId}/{skuId}")
    List<SpuSale> getSaleProductId(@PathVariable Long spuId,@PathVariable Long skuId);
    @RequestMapping("product/getSkuBySpuId/{skuId}")
    String getSkuBySpuId(@PathVariable Long skuId);
    @RequestMapping("product/getIndexCategory")
    List<JSONObject> getIndexCategory();
    @RequestMapping("product/onSale/{skuId}")
    Goods onSale(@PathVariable Long skuId);
}
