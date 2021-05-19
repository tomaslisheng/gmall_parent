package com.atguigu.gmall.service;

import java.util.Map;

/**
 * author lisheng
 * Date:2021/5/19
 * Description:
 */
public interface ProductService {
    Map<String, Object> getProductDetail(Long skuId);
}
