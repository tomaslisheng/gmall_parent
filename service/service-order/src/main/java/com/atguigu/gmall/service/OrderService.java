package com.atguigu.gmall.service;

import com.atguigu.gmall.order.OrderInfo;
import com.atguigu.result.Result;

/**
 * author lisheng
 * Date:2021/6/7
 * Description:
 */
public interface OrderService {
    OrderInfo getOrderView(String userId);

    OrderInfo submitOrder(String userId, String tradeNo, OrderInfo order);

    void cleanTradeNo(String userId, String tradeNo);

    String getTradeNo(String userId);

    OrderInfo getOrder(String userId);

    Result updateOrder(String outTradeNo);
}
