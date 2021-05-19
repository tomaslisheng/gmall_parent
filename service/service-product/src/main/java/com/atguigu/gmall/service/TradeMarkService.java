package com.atguigu.gmall.service;

import com.atguigu.gmall.product.BaseTrademark;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
public interface TradeMarkService {
    List<BaseTrademark> getTrademarkList();

    IPage<BaseTrademark> trademarkPage(Long page, Long limit);
}
