package com.atguigu.gmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.entity.GmallCorrelationData;
import com.atguigu.gmall.service.RabbitService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * author lisheng
 * Date:2021/6/16
 * Description:
 */
public class RabbitServiceImpl implements RabbitService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    //过期时间：分钟
    public static final int OBJECT_TIMEOUT = 10;

    @Override
    public void sendMessage(String exchange, String routingKey, Object message) {
        GmallCorrelationData correlationData = new GmallCorrelationData();
        String correlationId = UUID.randomUUID().toString();
        correlationData.setId(correlationId);
        correlationData.setMessage(message);
        correlationData.setExchange(exchange);
        correlationData.setRoutingKey(routingKey);
        redisTemplate.opsForValue().set(correlationId, JSON.toJSONString(correlationData), OBJECT_TIMEOUT, TimeUnit.MINUTES);

    }
}
