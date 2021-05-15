package com.atguigu.gmall.controller;

import com.atguigu.gmall.product.Category1;
import com.atguigu.gmall.service.CateGoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/15
 * Description:
 */
@RestController
@RequestMapping("admin/product")
public class CategroyController {
@Autowired
private CateGoryService cateGoryService;
    @RequestMapping("getCategory1")
    public List<Category1> getCategory1(){
        List<Category1> category1 = cateGoryService.getCategory1();
        return category1;
    }
}
