package com.atguigu.gmall.controller;

import com.atguigu.gmall.service.UserService;
import com.atguigu.gmall.user.UserInfo;
import com.atguigu.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * author lisheng
 * Date:2021/6/2
 * Description:
 */
@RestController
@RequestMapping("/api/user/")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("verify/{cookieOrHeaderValue}")
    public HashMap<String,Object> verify(@PathVariable String cookieOrHeaderValue){
        HashMap<String,Object> verifyMap = userService.verify(cookieOrHeaderValue);
        return verifyMap;
    }

    @RequestMapping("/passport/login")
    public Result login(@RequestBody UserInfo userInfo){
        UserInfo user = userService.login(userInfo);
        if(user==null){
            return Result.fail();
        }
        else{
            return Result.ok(user);
        }
    }


}
