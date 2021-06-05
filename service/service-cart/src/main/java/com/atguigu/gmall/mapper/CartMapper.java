package com.atguigu.gmall.mapper;

import com.atguigu.gmall.cart.CartInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * author lisheng
 * Date:2021/6/1
 * Description:
 */
@Mapper
public interface CartMapper extends BaseMapper<CartInfo> {
}
