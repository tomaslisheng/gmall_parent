package com.atguigu.gmall.mapper;

import com.atguigu.gmall.product.SpuSale;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Mapper
public interface SpuSaleMapper extends BaseMapper<SpuSale> {
    List<SpuSale> getSaleProductId(@Param("spuId") Long spuId, @Param("skuId")Long skuId);
}
