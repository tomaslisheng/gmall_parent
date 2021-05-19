package com.atguigu.gmall.service;

import com.atguigu.gmall.product.SkuInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
public interface SkuService {
    IPage<SkuInfo> listPage(Long page, Long limit);

    void onSale(Long skuId);

    void cancelSale(Long skuId);

    void saveSkuInfo(SkuInfo skuInfo);
}
