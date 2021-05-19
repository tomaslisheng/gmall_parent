package com.atguigu.gmall.controller;

import com.atguigu.gmall.product.Category1;
import com.atguigu.gmall.product.Category2;
import com.atguigu.gmall.product.Category3;
import com.atguigu.gmall.service.CateGoryService;
import com.atguigu.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
@CrossOrigin
public class CategroyApiController {
@Autowired
private CateGoryService cateGoryService;
    @RequestMapping("getCategory1")
    public Result getCategory1(){
        List<Category1> category1 = cateGoryService.getCategory1();
        return Result.ok(category1);
    }

    @RequestMapping("getCategory2/{category1Id}")
    public Result getCategory2(@PathVariable String category1Id){
        List<Category2> category2 = cateGoryService.getCategory2(category1Id);
        return Result.ok(category2);
    }

    @RequestMapping("getCategory3/{category2Id}")
    public Result getCategory3(@PathVariable String category2Id){
        List<Category3> category3 = cateGoryService.getCategory3(category2Id);
        return Result.ok(category3);
    }
}
