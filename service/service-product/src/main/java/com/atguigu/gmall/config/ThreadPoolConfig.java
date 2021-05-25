package com.atguigu.gmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author lisheng
 * Date:2021/5/25
 * Description:
 */
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ThreadPoolExecutor createThreadPool2(){
        return  new ThreadPoolExecutor(50,100,3, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10000));
    }

}
