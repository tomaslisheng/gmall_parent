package com.atguigu.gmall.client;

import com.atguigu.gmall.cart.CartInfo;
import com.atguigu.gmall.product.SkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * author lisheng
 * Date:2021/6/1
 * Description:
 */
@FeignClient("service-cart")
public interface CartFeignClient {
    @RequestMapping("api/cart/addCart/{skuId}/{skuNum}")
    SkuInfo addCart(@PathVariable long skuId, @PathVariable long skuNum);
    @RequestMapping("api/cart/getCartChecked/{userId}")
    List<CartInfo> getCartChecked(@PathVariable String userId);
}
