package com.atguigu.gmall.mapper;

import com.atguigu.gmall.product.SkuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Mapper
public interface SkuMapper extends BaseMapper<SkuInfo> {
    List<Map<String, Object>> selectSkuBySpuId(@Param("spuId") Long spuId);
}
