package com.atguigu.gmall.service;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.list.SearchParam;
import com.atguigu.gmall.model.list.SearchResponseVo;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/26
 * Description:
 */
public interface SearchService {
    List<JSONObject> getIndexCategory();

    SearchResponseVo search(SearchParam searchParam);

}
