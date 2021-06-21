package com.atguigu.gmall.service;

import com.atguigu.gmall.order.OrderInfo;

/**
 * author lisheng
 * Date:2021/6/7
 * Description:
 */
public interface OrderService {
    OrderInfo getOrderView(String userId,String tradeNo);
}
