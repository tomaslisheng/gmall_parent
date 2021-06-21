package com.atguigu.gmall.service;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.entity.GmallCorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


public interface RabbitService {



    //过期时间：分钟
    public static final int OBJECT_TIMEOUT = 10;

    /**
     *  发送消息
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param message 消息
     */

    public void sendMessage(String exchange, String routingKey, Object message);

}
