package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.client.ProductFeignClient;
import com.atguigu.gmall.service.ItemService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * author lisheng
 * Date:2021/5/19
 * Description:
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ProductFeignClient productFeignClient;
    @Override
    public Map<String, Object> getProductDetail(Long skuId) {
        Map<String, Object> map = productFeignClient.getProductDetail(skuId);
        return map;
    }
}
