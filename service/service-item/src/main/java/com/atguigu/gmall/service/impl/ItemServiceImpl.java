package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.client.ProductFeignClient;
import com.atguigu.gmall.product.CategoryView;
import com.atguigu.gmall.product.SkuImage;
import com.atguigu.gmall.product.SkuInfo;
import com.atguigu.gmall.product.SpuSale;
import com.atguigu.gmall.service.ItemService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * author lisheng
 * Date:2021/5/19
 * Description:
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ProductFeignClient productFeignClient;
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    /**
     * 异步编排查询商品详情信息
     * @param skuId
     * @return
     */
    @Override
    public Map<String, Object> getProductDetail(Long skuId) {
        HashMap<String, Object> map = new HashMap<>();
        /* Map<String, Object> map = productFeignClient.getProductDetail(skuId);*/
        //要返回值，异步
        CompletableFuture<SkuInfo> skuInfoFuture = CompletableFuture.supplyAsync(new Supplier<SkuInfo>() {
            @Override
            public SkuInfo get() {
                SkuInfo skuInfo = productFeignClient.getProduct(skuId);
                return skuInfo;
            }
        },threadPoolExecutor);
        //依赖上上面skuInfo线程
        CompletableFuture<Void> categoryFuture = skuInfoFuture.thenAcceptAsync(new Consumer<SkuInfo>() {
            @Override
            public void accept(SkuInfo skuInfo) {
                CategoryView categoryView = productFeignClient.getCategoryView(skuId);
                map.put("categoryView",categoryView);
            }
        },threadPoolExecutor);

        //依赖上上面skuInfo线程
        CompletableFuture<Void> imgFuture = skuInfoFuture.thenAcceptAsync(new Consumer<SkuInfo>() {
            @Override
            public void accept(SkuInfo skuInfo) {
                List<SkuImage> skuImageList = productFeignClient.getSkuImageList(skuId);
                skuInfo.setSkuImageList(skuImageList);
                map.put("skuInfo",skuInfo);
                map.put("price",skuInfo.getPrice());
            }
        },threadPoolExecutor);

        //依赖上上面skuInfo线程
        CompletableFuture<Void> saleFuture = skuInfoFuture.thenAcceptAsync(new Consumer<SkuInfo>() {
            @Override
            public void accept(SkuInfo skuInfo) {
                List<SpuSale> saleProductId = productFeignClient.getSaleProductId(skuInfo.getSpuId(),skuId);
                map.put("spuSaleAttrList",saleProductId);
            }
        },threadPoolExecutor);

        //依赖上上面skuInfo线程
        CompletableFuture<Void> valuesSkuJsonFuture = skuInfoFuture.thenAcceptAsync(new Consumer<SkuInfo>() {
            @Override
            public void accept(SkuInfo skuInfo) {
                String valuesSkuJson = productFeignClient.getSkuBySpuId(skuInfo.getSpuId());
                map.put("valuesSkuJson",valuesSkuJson);
            }
        },threadPoolExecutor);
        //allOf等待所有异步线程任务结束
        CompletableFuture.allOf(skuInfoFuture,categoryFuture,imgFuture,saleFuture,valuesSkuJsonFuture).join();
        //写入价格信息
        return map;
    }
    @Override
    public SkuInfo getProduct(Long skuId) {
        SkuInfo skuInfo = productFeignClient.getProduct(skuId);
        return skuInfo;
    }

    @Override
    public CategoryView getCategoryView(Long skuId) {
        CategoryView  categoryView= productFeignClient.getCategoryView(skuId);
        return categoryView;
    }
    @Override
    public List<SkuImage> getSkuImageList(Long skuId) {
        List<SkuImage> map = productFeignClient.getSkuImageList(skuId);
        return map;
    }
    @Override
    public List<SpuSale> getSaleProductId(Long spuId,Long skuId) {
        List<SpuSale> map = productFeignClient.getSaleProductId(spuId,skuId);
        return map;
    }
    @Override
    public String getSkuBySpuId(Long spuId) {
        String map = productFeignClient.getSkuBySpuId(spuId);
        return map;
    }




}
