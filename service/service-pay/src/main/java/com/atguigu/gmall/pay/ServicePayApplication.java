package com.atguigu.gmall.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * author lisheng
 * Date:2021/5/15
 * Description:
 */
@SpringBootApplication
@MapperScan("com.atguigu.gmall.pay.mapper")
@EnableDiscoveryClient
@EnableFeignClients("com.atguigu.gmall.client")
public class ServicePayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePayApplication.class,args);
    }
}
