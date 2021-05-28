package com.atguigu.gmall.service;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.list.SearchParam;
import com.atguigu.gmall.model.list.SearchResponseVo;

import java.io.IOException;
import java.util.List;

/**
 * author lisheng
 * Date:2021/5/26
 * Description:
 */
public interface SerachService {
    List<JSONObject> getIndexCategory();

    void onSale(Long skuId);

    void cancelSale(Long skuId);

    SearchResponseVo search(SearchParam searchParam) throws IOException;

    void createIndex(String index, String type);
}
