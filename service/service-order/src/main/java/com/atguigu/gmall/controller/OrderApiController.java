package com.atguigu.gmall.controller;

import com.atguigu.gmall.order.OrderInfo;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * author lisheng
 * Date:2021/6/7
 * Description:
 */
@RestController
@RequestMapping("/api/order")
public class OrderApiController {

    @Autowired
    private OrderService orderService;
    @RequestMapping("getOrderView/{userId}")
    public  OrderInfo getOrderView(@PathVariable String userId){
        OrderInfo orderInfo = orderService.getOrderView(userId);
        return orderInfo;
    }

    @RequestMapping("getOrder/{orderId}")
    public  OrderInfo getOrder(@PathVariable String orderId){
        OrderInfo orderInfo = orderService.getOrder(orderId);
        return orderInfo;
    }

    @RequestMapping("updateOrder/{outTradeNo}")
    public  Result updateOrder(@PathVariable String outTradeNo){
        Result result = orderService.updateOrder(outTradeNo);
        return result;
    }


    /**
     * 保存订单
     * @param tradeNo
     * @return
     */
    @RequestMapping("auth/submitOrder")
    public Result submitOrder(HttpServletRequest request, @RequestBody OrderInfo order, String tradeNo){
        String userId = request.getHeader("userId");
        //先根据唯一码判断，当前订单是否创建
        String redisTradeNo = orderService.getTradeNo(userId);
        OrderInfo  orderInfo = new OrderInfo();
        if(!StringUtils.isEmpty(redisTradeNo)&&redisTradeNo.equals(tradeNo)){
            orderInfo = orderService.submitOrder(userId,tradeNo,order);
            //订单保存成功，销毁订单唯一码
            orderService.cleanTradeNo(userId,tradeNo);
            return Result.ok(orderInfo.getId().toString());
        }
        else{
            return Result.fail();
        }


    }
}
