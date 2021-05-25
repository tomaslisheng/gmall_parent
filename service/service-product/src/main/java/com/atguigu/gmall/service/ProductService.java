package com.atguigu.gmall.service;

import com.atguigu.gmall.product.CategoryView;
import com.atguigu.gmall.product.SkuImage;
import com.atguigu.gmall.product.SkuInfo;
import com.atguigu.gmall.product.SpuSale;

import java.util.List;
import java.util.Map;

/**
 * author lisheng
 * Date:2021/5/19
 * Description:
 */
public interface ProductService {
    Map<String, Object> getProductDetail(Long skuId) throws InterruptedException;
    SkuInfo getProduct(Long skuId) throws InterruptedException;
    CategoryView getCategoryView(Long category3Id);
    List<SkuImage> getSkuImageList(Long skuId);
    List<SpuSale> getSaleProductId(Long spuId, Long skuId);
    String getSkuBySpuId(Long spuId);
}
