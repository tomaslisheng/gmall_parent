package com.atguigu.gmall.controller;

import com.atguigu.gmall.product.SkuInfo;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.result.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/product")
public class SkuApiController {

    /**
     * SKU分页查询
     */
    @Autowired
    private SkuService skuService;
    @RequestMapping("/list/{page}/{limit}")
    public Result listPage(@PathVariable Long page,@PathVariable Long limit){
        IPage<SkuInfo> iPage = skuService.listPage(page,limit);
        return Result.ok(iPage);
    }

    @PostMapping("saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfo skuInfo){
        skuService.saveSkuInfo(skuInfo);
        return Result.ok();
    }
    /**
     * 上架
     * @param skuId
     * @return
     */
    @RequestMapping("onSale/{skuId}")
    public Result onSale(@PathVariable Long skuId){
        skuService.onSale(skuId);
        return Result.ok();
    }

    /**
     * 下架
     * @param skuId
     * @return
     */
    @RequestMapping("cancelSale/{skuId}")
    public Result cancelSale(@PathVariable Long skuId){
        skuService.cancelSale(skuId);
        return Result.ok();
    }

}
