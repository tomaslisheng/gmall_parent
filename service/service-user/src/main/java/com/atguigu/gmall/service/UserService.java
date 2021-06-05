package com.atguigu.gmall.service;

import com.atguigu.gmall.user.UserInfo;

import java.util.HashMap;

/**
 * author lisheng
 * Date:2021/6/2
 * Description:
 */
public interface UserService {
    HashMap<String, Object> verify(String cookieOrHeaderValue);

    UserInfo login(UserInfo userInfo);
}
