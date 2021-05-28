package com.atguigu.gmall.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.list.SearchParam;
import com.atguigu.gmall.model.list.SearchResponseVo;
import com.atguigu.gmall.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/26
 * Description:
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;
    @RequestMapping("index.html")
    public String index(Model model){
        List<JSONObject> jsonObjects = searchService.getIndexCategory();
        model.addAttribute("list",jsonObjects);
        return "index/index";
    }

    @RequestMapping("search.html")
    public String search(Model model, SearchParam searchParam){
        SearchResponseVo searchResponseVo= searchService.search(searchParam);
        model.addAttribute("goodsList",searchResponseVo.getGoodsList());
        model.addAttribute("trademarkList",searchResponseVo.getTrademarkList());
        return "list/index";
    }


}
