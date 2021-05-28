package com.atguigu.gmall.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.list.SearchParam;
import com.atguigu.gmall.model.list.SearchResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.atguigu.gmall.service.SerachService;

import java.io.IOException;
import java.util.List;

/**
 * author lisheng
 * Date:2021/5/26
 * Description:
 */
@RestController
@RequestMapping("index/search")
public class SearchApiController {

    @Autowired
    private SerachService searchService;
    @RequestMapping("getIndexCategory")
    public  List<JSONObject> getIndexCategory(){
        List<JSONObject> jsonObjects = searchService.getIndexCategory();
        return jsonObjects;
    }
    @RequestMapping("onSale/{skuId}")
    public void onSale(@PathVariable Long skuId){
        searchService.onSale(skuId);
    }

    @RequestMapping("cancelSale/{skuId}")
    public void cancelSale(@PathVariable Long skuId){
        searchService.cancelSale(skuId);
    }

    @RequestMapping("searchParam")
    public  SearchResponseVo search(@RequestBody SearchParam searchParam) throws IOException {
        SearchResponseVo searchResponseVo = searchService.search(searchParam);
        return searchResponseVo;
    }

    @RequestMapping("createIndex")
    public void createIndex(){
        System.out.println(134);
        searchService.createIndex("com.atguigu.gmall.model.list.Goods","2");
    }

}
