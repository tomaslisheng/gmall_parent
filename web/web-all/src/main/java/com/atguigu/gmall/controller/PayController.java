package com.atguigu.gmall.controller;

import com.atguigu.gmall.order.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * author lisheng
 * Date:2021/6/8
 * Description:
 */
@Controller
@Slf4j
public class PayController {

    @RequestMapping("pay.html")
    public String pay(String orderId, Model model){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(Long.parseLong(orderId));
        model.addAttribute("orderInfo",orderInfo);
        return "payment/pay";
    }
}
