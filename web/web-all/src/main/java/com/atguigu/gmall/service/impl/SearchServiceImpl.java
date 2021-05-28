package com.atguigu.gmall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.client.SearchFeignClient;
import com.atguigu.gmall.model.list.SearchParam;
import com.atguigu.gmall.model.list.SearchResponseVo;
import com.atguigu.gmall.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/26
 * Description:
 */
@Service
public class SearchServiceImpl implements SearchService {
@Autowired
private SearchFeignClient searchFeignClient;

    @Override
    public List<JSONObject> getIndexCategory() {
        List<JSONObject> jsonObjects = searchFeignClient.getIndexCategory();
        return jsonObjects;
    }

    @Override
    public SearchResponseVo search(SearchParam searchParam) {
        SearchResponseVo searchResponseVo = searchFeignClient.search(searchParam);
        return searchResponseVo;
    }


}
