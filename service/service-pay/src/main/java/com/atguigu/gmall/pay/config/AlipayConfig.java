package com.atguigu.gmall.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author lisheng
 * Date:2021/6/9
 * Description:
 */
@Configuration
public class AlipayConfig {
    @Value("${ali.app_id}")
    public String appId;
    @Value("${ali.app_private_key}")
    public String app_private_key;
    @Value("${ali.app_public_key}")
    public String app_public_key;
    @Value("${ali.return_payment_url}")
    public String return_payment_url;
    @Value("${ali.notify_payment_url}")
    public String notify_payment_url;
    @Value("${ali.SIGN_TYPE}")
    public String SIGN_TYPE;
    @Value("${ali.FORMAT}")
    public String FORMAT;
    @Value("${ali.CHARSET}")
    public String CHARSET;
    @Value("${ali.alipay_url}")
    public String alipay_url;

    @Bean
    public AlipayClient alipayClient(){
        AlipayClient alipayClient = new DefaultAlipayClient(alipay_url,appId,app_private_key,FORMAT,CHARSET,app_public_key,SIGN_TYPE);
        return  alipayClient;
    }

}
