package com.atguigu.gmall.service.impl;

import com.alibaba.nacos.common.util.Md5Utils;
import com.atguigu.gmall.mapper.UserMapper;
import com.atguigu.gmall.service.UserService;
import com.atguigu.gmall.user.UserInfo;
import com.atguigu.result.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sun.misc.CharacterEncoder;

import java.util.HashMap;
import java.util.UUID;

/**
 * author lisheng
 * Date:2021/6/2
 * Description:
 */
@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public HashMap<String, Object> verify(String token) {
        HashMap<String, Object> verifyMap = new HashMap<>();
        //从缓存获取
        UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get("user:login:token:" + token);
        if(userInfo!=null){
            verifyMap.put("userinfo",userInfo);
            verifyMap.put("userId",userInfo.getId());
            verifyMap.put("success","success");
        }
        else{
            verifyMap.put("success","fail");
        }
        return verifyMap;
    }

    @Override
    public UserInfo login(UserInfo userInfo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("passwd", MD5.encrypt(userInfo.getPasswd()));
        queryWrapper.eq("login_name",userInfo.getLoginName());
        UserInfo userInfo1 = userMapper.selectOne(queryWrapper);
        if(userInfo1!=null){
            //登录通过，生成token 写入redis
            String token = UUID.randomUUID().toString();
            userInfo1.setToken(token);

            redisTemplate.opsForValue().set("user:login:token:" + token,userInfo1);
        }
        return userInfo1;
    }
}
