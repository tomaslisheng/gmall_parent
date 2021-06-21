package com.atguigu.gmall.client;

import com.atguigu.gmall.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

/**
 * author lisheng
 * Date:2021/6/2
 * Description:
 */
@FeignClient("service-user")
public interface UserFeignClient {
    @RequestMapping("/api/user/verify/{cookieOrHeaderValue}")
    public HashMap<String,Object> verify(@PathVariable String cookieOrHeaderValue);
    @RequestMapping("/api/user/getUserAddress/{userId}")
    List<UserAddress> getUserAddress(@PathVariable String userId);
}
