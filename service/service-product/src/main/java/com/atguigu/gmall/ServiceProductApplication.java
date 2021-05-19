package com.atguigu.gmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * author lisheng
 * Date:2021/5/15
 * Description:
 */
@SpringBootApplication()
@MapperScan("com.atguigu.gmall.mapper")
@EnableDiscoveryClient
public class ServiceProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceProductApplication.class,args);
    }
}
