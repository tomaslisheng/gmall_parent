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
    /**
     * 1	corePoolSize	int	核心线程池大小
     * 2	maximumPoolSize	int	最大线程池大小
     * 3	keepAliveTime	long	线程最大空闲时间
     * 4	unit	TimeUnit	时间单位
     * 5	workQueue	BlockingQueue<Runnable>	线程等待队列
     * #6	threadFactory	ThreadFactory	线程创建工厂
     * #7	handler	RejectedExecutionHandler	拒绝策略
     */
    @Bean
    public ThreadPoolExecutor createThreadPool2(){
        return  new ThreadPoolExecutor(50,100,3, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10000));
    }

}
