package com.atguigu.gmall.pay.mapper;

import com.atguigu.gmall.payment.PaymentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * author lisheng
 * Date:2021/6/15
 * Description:
 */
@Mapper
public interface PayMapper extends BaseMapper<PaymentInfo> {
}
