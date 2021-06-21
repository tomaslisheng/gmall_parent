package com.atguigu.gmall.client;

import com.atguigu.gmall.order.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * author lisheng
 * Date:2021/6/2
 * Description:
 */
@FeignClient("service-order")
public interface OrderFeignClient {
    @RequestMapping("/api/user/verify/{cookieOrHeaderValue}")
    HashMap<String,Object> verify(@PathVariable String cookieOrHeaderValue);
    @RequestMapping("/api/order/getOrderView/{userId}")
    OrderInfo getOrderView(@PathVariable String userId);
    @RequestMapping("/api/order/getOrder/{orderId}")
    OrderInfo getOrder(@PathVariable String orderId);
    @RequestMapping("/api/order/updateOrder/{outTradeNo}")
    void updateOrder(String outTradeNo);
}
