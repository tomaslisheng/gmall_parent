package com.atguigu.gmall.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.gmall.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author lisheng
 * Date:2021/6/9
 * Description:
 */
@Slf4j
@RestController
@RequestMapping("/api/payment/alipay")
public class PayController {
    @Autowired
    private PayService payService;

    @RequestMapping("submit/{orderId}")
    private String submit(HttpServletRequest request,@PathVariable String orderId){
        String userId = request.getHeader("userId");
        String from = payService.submit(userId,orderId);
        return from;
    }

    @RequestMapping("callback/return")
    private String callback(HttpServletRequest request){
        System.out.println(request);
        payService.callback(request);
        return "成功";
    }


    /*public   void   doPost (HttpServletRequest httpRequest,
                            HttpServletResponse httpResponse)   throws ServletException, IOException {


        AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request
        alipayRequest.setReturnUrl( "http://domain.com/CallBack/return_url.jsp" );
        alipayRequest.setNotifyUrl( "http://domain.com/CallBack/notify_url.jsp" ); //在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent( "{"  +
                "    \"out_trade_no\":\"20150320010101001\","  +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\","  +
                "    \"total_amount\":88.88,"  +
                "    \"subject\":\"Iphone6 16G\","  +
                "    \"body\":\"Iphone6 16G\","  +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","  +
                "    \"extend_params\":{"  +
                "    \"sys_service_provider_id\":\"2088511833207846\""  +
                "    }" +
                "  }" ); //填充业务参数
        String form= "" ;
        try  {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        }  catch  (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType( "text/html;charset="  + CHARSET);
        httpResponse.getWriter().write(form); //直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }*/

}
