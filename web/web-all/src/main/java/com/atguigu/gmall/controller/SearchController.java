package com.atguigu.gmall.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.list.SearchAttr;
import com.atguigu.gmall.model.list.SearchParam;
import com.atguigu.gmall.model.list.SearchResponseAttrVo;
import com.atguigu.gmall.model.list.SearchResponseVo;
import com.atguigu.gmall.service.SearchService;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        model.addAttribute("attrsList",searchResponseVo.getAttrsList());
        model.addAttribute("urlParam",getUrlParam(searchParam));
        String[] props = searchParam.getProps();
        if(!StringUtils.isEmpty(searchParam.getTrademark())){
            model.addAttribute("trademarkParam",searchParam.getTrademark().split(":")[1]);
        }
        List<SearchAttr> propsParamList = new ArrayList<>();
        if(props!=null&&props.length>0){
            for (String prop : props) {
                SearchAttr searchAttr = new SearchAttr();
               String attrId = prop.split(":")[0];
               String attrValue = prop.split(":")[1];
               String attrName = prop.split(":")[2];
                searchAttr.setAttrId(Long.parseLong(attrId));
                searchAttr.setAttrName(attrName);
                searchAttr.setAttrValue(attrValue);
                propsParamList.add(searchAttr);
            }
            model.addAttribute("propsParamList",propsParamList);
        }
        if(!StringUtils.isEmpty(searchParam.getOrder())){
            Map<String,String> orderMap = new HashMap<>();
            orderMap.put("type",searchParam.getOrder().split(":")[0]);
            orderMap.put("sort",searchParam.getOrder().split(":")[1]);
            model.addAttribute("orderMap",orderMap);
        }
        return "list/index";
    }

    public  String getUrlParam(SearchParam searchParam){
        String url="search.html?";
        Long category3Id = searchParam.getCategory3Id();
        String keyword = searchParam.getKeyword();
        String trademark = searchParam.getTrademark();
        String[] props = searchParam.getProps();

        if(!StringUtils.isEmpty(category3Id)){
            url+="category3Id="+category3Id;
        }
        if(!StringUtils.isEmpty(keyword)){
            url+="keyword="+keyword;
        }
        if(!StringUtils.isEmpty(trademark)){
            url+="&trademark="+trademark;
        }
        if(props!=null&&props.length>0){
            for (String prop : props) {
                url+="&props="+prop;
            }

        }
        return url;
    }

}
