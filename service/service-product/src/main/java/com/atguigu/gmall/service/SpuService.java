package com.atguigu.gmall.service;

import com.atguigu.gmall.product.BaseSale;
import com.atguigu.gmall.product.SpuImage;
import com.atguigu.gmall.product.SpuInfo;
import com.atguigu.gmall.product.SpuSale;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
public interface SpuService {
    IPage<SpuInfo> getSpuPage(Long page, Long limit, Long catgory3Id);

    List<BaseSale> baseSaleAttrList();

    void saveSpuInfo(SpuInfo spuInfo);

    List<SpuSale> spuSaleAttrList(Long spuId);

    List<SpuImage> spuImageList(Long spuId);
}
