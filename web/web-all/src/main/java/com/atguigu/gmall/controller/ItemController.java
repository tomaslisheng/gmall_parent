package com.atguigu.gmall.controller;

import com.atguigu.gmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * author lisheng
 * Date:2021/5/19
 * Description:
 */
@Controller
public class ItemController {
    @Autowired
    private ProductService productService;
    @RequestMapping("{skuId}.html")
    public String getProductDetail(@PathVariable Long skuId, Model model){

        Map<String,Object> map = productService.getProductDetail(skuId);
        model.addAllAttributes(map);
        return "item/index";
    }
}
