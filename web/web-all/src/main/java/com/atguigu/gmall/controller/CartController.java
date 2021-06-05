package com.atguigu.gmall.controller;

import com.atguigu.gmall.product.SkuInfo;
import com.atguigu.gmall.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * author lisheng
 * Date:2021/6/1
 * Description:
 */
@Controller
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;
    @RequestMapping("/addCart.html")
    public String addCart(HttpServletRequest request, Model model, long skuId, long skuNum){
        String userId = request.getHeader("userId");
        String userTempId = request.getHeader("userTempId");
        SkuInfo skuInfo = cartService.addCart(skuId,skuNum);
        model.addAttribute("skuInfo",skuInfo);
        return "cart/addCart";
    }
    @RequestMapping("/cart.html")
    public String cart(Model model){

        return "cart/index";
    }

}
