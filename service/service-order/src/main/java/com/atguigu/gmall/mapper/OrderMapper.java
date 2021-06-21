package com.atguigu.gmall.mapper;

import com.atguigu.gmall.order.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * author lisheng
 * Date:2021/6/8
 * Description:
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {
}
