package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.client.OrderFeignClient;
import com.atguigu.gmall.order.OrderInfo;
import com.atguigu.gmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * author lisheng
 * Date:2021/6/7
 * Description:
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderFeignClient orderFeignClient;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public OrderInfo getOrderView(String userId,String tradeNo) {
        OrderInfo orderInfo = orderFeignClient.getOrderView(userId);
        //将订单唯一码存入redis中，唯一码，防止 订单重复提交
        redisTemplate.opsForValue().set("tradeNo:userId:"+userId,tradeNo);
        return orderInfo;
    }
}
