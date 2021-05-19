package com.atguigu.gmall.service;

import com.atguigu.gmall.product.Category1;
import com.atguigu.gmall.product.Category2;
import com.atguigu.gmall.product.Category3;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/15
 * Description:
 */
public interface CateGoryService {
    List<Category1> getCategory1();

    List<Category2> getCategory2(String categoryId);

    List<Category3> getCategory3(String categoryId);
}
