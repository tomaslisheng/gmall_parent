package com.atguigu.gmall.service;

import com.atguigu.gmall.product.SkuInfo;

/**
 * author lisheng
 * Date:2021/6/1
 * Description:
 */
public interface CartService {
    SkuInfo addCart(long skuId, long skuNum);
}
