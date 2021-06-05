package com.atguigu.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * author lisheng
 * Date:2021/5/15
 * Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceGateWayApplication.class,args);
    }
}
