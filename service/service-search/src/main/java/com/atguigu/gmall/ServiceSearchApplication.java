package com.atguigu.gmall;

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
@SpringBootApplication()
@MapperScan("com.atguigu.gmall.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSearchApplication.class,args);
    }
}
