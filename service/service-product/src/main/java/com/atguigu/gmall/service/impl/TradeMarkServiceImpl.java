package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.mapper.TradeMarkMapper;
import com.atguigu.gmall.product.BaseTrademark;
import com.atguigu.gmall.product.SpuInfo;
import com.atguigu.gmall.service.TradeMarkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Service
public class TradeMarkServiceImpl implements TradeMarkService {

    @Autowired
    private TradeMarkMapper tradeMarkMapper;
    @Override
    public List<BaseTrademark> getTrademarkList() {
        List<BaseTrademark> baseTrademarks = tradeMarkMapper.selectList(null);
        return baseTrademarks;
    }

    @Override
    public IPage<BaseTrademark> trademarkPage(Long page, Long limit) {
        IPage<BaseTrademark> page1 = new Page<BaseTrademark>(page,limit);
        IPage<BaseTrademark> spuInfoIPage = tradeMarkMapper.selectPage(page1,null);
        return spuInfoIPage;
    }
}
