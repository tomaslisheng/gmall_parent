package com.atguigu.gmall.pay.service;

import javax.servlet.http.HttpServletRequest;

/**
 * author lisheng
 * Date:2021/6/9
 * Description:
 */
public interface PayService {
    String submit(String userId, String orderId);

    void callback(HttpServletRequest request);
}
