package com.atguigu.gmall.controller;

import com.atguigu.gmall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
