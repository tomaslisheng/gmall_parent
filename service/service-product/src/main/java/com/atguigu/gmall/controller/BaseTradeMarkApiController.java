package com.atguigu.gmall.controller;

import com.atguigu.gmall.product.BaseTrademark;
import com.atguigu.gmall.service.TradeMarkService;
import com.atguigu.result.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@RestController
@RequestMapping("admin/product/baseTrademark")
@CrossOrigin
public class BaseTradeMarkApiController {
    @Autowired
    private TradeMarkService tradeMarkService;
    @RequestMapping("getTrademarkList")
    public Result getTrademarkList(){
        List<BaseTrademark> tradeMarkList = tradeMarkService.getTrademarkList();
        return Result.ok(tradeMarkList);
    }
    @GetMapping("{page}/{limit}")
    public Result trademarkPage(@PathVariable Long page,@PathVariable Long limit){
        IPage<BaseTrademark> iPage = tradeMarkService.trademarkPage(page,limit);
        return Result.ok(iPage);
    }

}
