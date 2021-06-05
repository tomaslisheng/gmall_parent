package com.atguigu.gmall.controller;

import com.atguigu.gmall.cart.CartInfo;
import com.atguigu.gmall.product.SkuInfo;
import com.atguigu.gmall.service.CartService;
import com.atguigu.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * author lisheng
 * Date:2021/6/1
 * Description:
 */

@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @RequestMapping("/addCart/{skuId}/{skuNum}")
    public SkuInfo addCart(HttpServletRequest request,@PathVariable Long skuId,@PathVariable Long skuNum){
        String userId = request.getHeader("userId");
        String userTempId = request.getHeader("userTempId");
        SkuInfo skuInfo = cartService.addCart(skuId,skuNum,userId,userTempId);
        return skuInfo;
    }
    @RequestMapping("/addToCart/{skuId}/{skuNum}")
    public Result addToCart(HttpServletRequest request,@PathVariable Long skuId,@PathVariable Long skuNum){
        String userId = request.getHeader("userId");
        String userTempId = request.getHeader("userTempId");
        cartService.addToCart(skuId,skuNum,userId,userTempId);
        return  Result.ok() ;
    }

    @RequestMapping("/cartList")
    public Result cartList(HttpServletRequest request){
        String userId = request.getHeader("userId");
        String userTempId = request.getHeader("userTempId");
        List<CartInfo> data = cartService.cartList(userId,userTempId);
        return Result.ok(data);
    }

    @RequestMapping("/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable Long skuId,@PathVariable Long isChecked){
        cartService.checkCart(skuId,isChecked);
        return Result.ok();
    }

    @RequestMapping("/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable Long skuId){
        cartService.deleteCart(skuId);
        return Result.ok();
    }

}
