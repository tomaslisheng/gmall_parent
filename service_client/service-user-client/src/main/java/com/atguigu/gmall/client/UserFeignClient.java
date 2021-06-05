package com.atguigu.gmall.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * author lisheng
 * Date:2021/6/2
 * Description:
 */
@FeignClient("service-user")
public interface UserFeignClient {
    @RequestMapping("/api/user/verify/{cookieOrHeaderValue}")
    public HashMap<String,Object> verify(@PathVariable String cookieOrHeaderValue);
}
