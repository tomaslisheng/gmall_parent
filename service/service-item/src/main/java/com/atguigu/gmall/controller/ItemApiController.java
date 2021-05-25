package com.atguigu.gmall.controller;

import com.atguigu.gmall.product.CategoryView;
import com.atguigu.gmall.product.SkuImage;
import com.atguigu.gmall.product.SkuInfo;
import com.atguigu.gmall.product.SpuSale;
import com.atguigu.gmall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * author lisheng
 * Date:2021/5/19
 * Description:
 */
@RestController
@RequestMapping("/item")
@CrossOrigin
public class ItemApiController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/getProductDetail/{skuId}")
    public Map<String, Object> getProductDetail(@PathVariable Long skuId) {
        Map<String,Object> map = itemService.getProductDetail(skuId);
        return map;
    }

    @RequestMapping("getProduct/{skuId}")
    public SkuInfo getProduct (@PathVariable Long skuId) throws InterruptedException {
        SkuInfo skuInfo = itemService.getProduct(skuId);
        return skuInfo;
    }
    @RequestMapping("getCategoryView/{skuId}")
    public CategoryView getCategoryView(@PathVariable Long skuId) {
        CategoryView categoryView = itemService.getCategoryView(skuId);
        return categoryView;
    }

    @RequestMapping("getSkuImageList/{skuId}")
    public List<SkuImage> getSkuImageList (@PathVariable Long skuId) {
        List<SkuImage> skuImages = itemService.getSkuImageList(skuId);
        return skuImages;
    }

    @RequestMapping("getSaleProductId/{spuId}/{skuId}")
    public List<SpuSale> getSaleProductId (@PathVariable Long spuId, @PathVariable Long skuId) throws InterruptedException {
        List<SpuSale> spuSales = itemService.getSaleProductId(spuId,skuId);
        return spuSales;
    }

    @RequestMapping("getSkuBySpuId/{skuId}")
    public String getSkuBySpuId (@PathVariable Long skuId) throws InterruptedException {
        String skuInfo = itemService.getSkuBySpuId(skuId);
        return skuInfo;
    }

}
