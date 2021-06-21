package com.atguigu.gmall.controller;

import com.atguigu.gmall.order.OrderInfo;
import com.atguigu.gmall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * author lisheng
 * Date:2021/6/7
 * Description:
 */
@Controller
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;
    @RequestMapping("trade.html")
    public String trade(HttpServletRequest request, Model model){
        String userId = request.getHeader("userId");
        String tradeNo = String.valueOf(UUID.randomUUID());
        OrderInfo orderInfo = orderService.getOrderView(userId,tradeNo);
        //需要 显示一个临时订单信息。展示到页面
        model.addAttribute("order",orderInfo);
        model.addAttribute("detailArrayList",orderInfo.getOrderDetailList());
        model.addAttribute("userAddressList",orderInfo.getUserAddresses());
        model.addAttribute("totalAmount",orderInfo.getTotalAmount());
        model.addAttribute("totalNum",orderInfo.getTotalNum());
        model.addAttribute("tradeNo",tradeNo);

        return "order/trade";
    }
}
