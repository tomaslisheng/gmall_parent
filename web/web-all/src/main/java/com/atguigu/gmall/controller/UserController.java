package com.atguigu.gmall.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author lisheng
 * Date:2021/6/2
 * Description:
 */
@Controller
@Slf4j
public class UserController {



    /**
     * public String login(Model model, @RequestParam(required = true) String originUrl, Long skuId, Long skuNum)
     * 如何不添加requestParam 则参数不是必传值。如果使用 当前注解，默认true，则是必填项。false 为不不是必填项
     */
    @RequestMapping("login.html")
    public String login(Model model, String originUrl, Long skuId, Long skuNum){
        return "login";
    }
}
