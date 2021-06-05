package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.client.CartFeignClient;
import com.atguigu.gmall.product.SkuInfo;
import com.atguigu.gmall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

/**
 * author lisheng
 * Date:2021/6/1
 * Description:
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartFeignClient cartFeignClient;

    @Override
    public SkuInfo addCart(long skuId, long skuNum) {
        SkuInfo skuInfo = cartFeignClient.addCart(skuId,skuNum);
        return skuInfo;
    }
}
