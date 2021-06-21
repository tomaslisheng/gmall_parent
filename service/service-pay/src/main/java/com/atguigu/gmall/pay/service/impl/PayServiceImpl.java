package com.atguigu.gmall.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.gmall.client.OrderFeignClient;
import com.atguigu.gmall.enums.PaymentStatus;
import com.atguigu.gmall.enums.PaymentType;
import com.atguigu.gmall.order.OrderInfo;
import com.atguigu.gmall.pay.mapper.PayMapper;
import com.atguigu.gmall.pay.service.PayService;
import com.atguigu.gmall.payment.PaymentInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.bytebuddy.asm.Advice;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * author lisheng
 * Date:2021/6/9
 * Description:
 */
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    AlipayClient alipayClient;
    @Value("${ali.return_payment_url}")
    public String return_payment_url;
    @Value("${ali.notify_payment_url}")
    public String notify_payment_url;
    @Autowired
    private PayMapper payMapper;
    @Autowired(required = false)
    private OrderFeignClient orderFeignClient;
    @Override
    public String submit(String userId, String orderId) {
        AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request
        alipayRequest.setReturnUrl(return_payment_url);
        alipayRequest.setNotifyUrl(notify_payment_url); //在公共参数中设置回跳和通知地址
        OrderInfo orderInfo = orderFeignClient.getOrder(orderId);
        Map<String,Object> bizContent = new  HashMap<String,Object>();
        bizContent.put("out_trade_no",orderInfo.getOutTradeNo());
        bizContent.put("product_code","FAST_INSTANT_TRADE_PAY");
        bizContent.put("total_amount",0.01);
        bizContent.put("subject",orderInfo.getOrderDetailList().get(0).getSkuNum());
        alipayRequest.setBizContent(JSON.toJSONString(bizContent));

        String form= "" ;
        try  {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        }  catch  (AlipayApiException e) {
            e.printStackTrace();
        }

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOutTradeNo(orderInfo.getOutTradeNo());
        paymentInfo.setTotalAmount(orderInfo.getTotalAmount());
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setOrderId(orderInfo.getId());
        paymentInfo.setPaymentStatus(PaymentStatus.UNPAID.toString());
        paymentInfo.setSubject(orderInfo.getOrderDetailList().get(0).getSkuName());
        payMapper.insert(paymentInfo);
        return form;
    }

    @Override
    public void callback(HttpServletRequest request) {
        String trade_no = request.getParameter("trade_no");
        String out_trade_no = request.getParameter("out_trade_no");
        String queryString = request.getQueryString();

        //修改支付信息
        QueryWrapper<PaymentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trade_no",trade_no);
        queryWrapper.eq("out_trade_no",out_trade_no);
        PaymentInfo paymentInfo = payMapper.selectOne(queryWrapper);
        paymentInfo.setPaymentStatus(PaymentStatus.PAID.toString());
        paymentInfo.setCallbackContent(queryString);
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setPaymentType(PaymentType.ALIPAY.toString());
        payMapper.updateById(paymentInfo);

        //修改订单信息
        // TODO: 2021/6/16  orderFeignClient.updateOrder
        orderFeignClient.updateOrder(out_trade_no);

    }
}
