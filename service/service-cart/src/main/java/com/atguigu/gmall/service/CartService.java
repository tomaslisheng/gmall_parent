package com.atguigu.gmall.service;

import com.atguigu.gmall.cart.CartInfo;
import com.atguigu.gmall.product.SkuInfo;

import java.util.List;

/**
 * author lisheng
 * Date:2021/6/1
 * Description:
 */
public interface CartService {
    SkuInfo addCart(Long skuId, Long skuNum,String userId,String userTempId);

    List<CartInfo> cartList(String userId,String userTempId);

    void checkCart(Long skuId, Long isChecked,String userId,String userTempId);

    void deleteCart(Long skuId,String userId,String userTempId);

    void addToCart(Long skuId, Long skuNum,String userId,String userTempId);

    List<CartInfo> getCartChecked(String userId);
}
