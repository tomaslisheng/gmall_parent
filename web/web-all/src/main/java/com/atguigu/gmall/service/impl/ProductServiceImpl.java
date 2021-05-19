package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.client.ItemFeignClient;
import com.atguigu.gmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * author lisheng
 * Date:2021/5/19
 * Description:
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ItemFeignClient itemFeignClient;
    @Override
    public Map<String, Object> getProductDetail(Long skuId) {
        Map<String, Object> map = itemFeignClient.getProductDetail(skuId);
        return map;
    }
}
