package com.atguigu.gmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * author lisheng
 * Date:2021/5/19
 * Description:
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.atguigu.gmall.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceWebAllApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceWebAllApplication.class,args);
    }
}
