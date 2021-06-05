package com.atguigu.gmall.mapper;

import com.atguigu.gmall.user.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * author lisheng
 * Date:2021/6/2
 * Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {
}
