package com.atguigu.gmall.client;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.list.SearchParam;
import com.atguigu.gmall.model.list.SearchResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/26
 * Description:
 */
@FeignClient("service-search")
public interface SearchFeignClient {
    @RequestMapping("index/search/getIndexCategory")
    List<JSONObject> getIndexCategory();
    @RequestMapping("index/search/onSale/{skuId}")
    void onSale(@PathVariable Long skuId);
    @RequestMapping("index/search/cancelSale/{skuId}")
    void cancelSale(@PathVariable Long skuId);
    @RequestMapping("index/search/searchParam")
    SearchResponseVo search(SearchParam searchParam);
}
