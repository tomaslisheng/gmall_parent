package com.atguigu.gmall.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * author lisheng
 * Date:2021/5/19
 * Description:
 */
@FeignClient("service-item")
public interface ItemFeignClient {
    @RequestMapping("item/getProductDetail/{skuId}")
    public Map<String, Object> getProductDetail(@PathVariable Long skuId);
}
