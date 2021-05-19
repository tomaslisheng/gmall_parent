package com.atguigu.gmall.controller;

import com.atguigu.gmall.product.BaseSale;
import com.atguigu.gmall.product.SpuImage;
import com.atguigu.gmall.product.SpuInfo;
import com.atguigu.gmall.product.SpuSale;
import com.atguigu.gmall.service.SpuService;
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
@RequestMapping("admin/product")
@CrossOrigin
public class SpuApiController {
    @Autowired
    private SpuService spuService;

    /**
     * SPU 分页查询
     * @param page
     * @param limit
     * @param category3Id
     * @return
     */
    @GetMapping("{page}/{limit}")
    private Result getSpuPage(@PathVariable Long page,@PathVariable Long limit,Long category3Id){
        IPage<SpuInfo> spuInfoIPage = spuService.getSpuPage(page,limit,category3Id);
        return Result.ok(spuInfoIPage);
    }

    /**
     * 查询所有销售属性
     * @return
     */
    @RequestMapping("baseSaleAttrList")
    private Result  baseSaleAttrList(){
        List<BaseSale> sales = spuService.baseSaleAttrList();
        return Result.ok(sales);
    }

    /**
     * 保存SPU信息
     * @param spuInfo
     * @return
     */
    @PostMapping("saveSpuInfo")
    private Result  saveSpuInfo(@RequestBody SpuInfo spuInfo){
        spuService.saveSpuInfo(spuInfo);
        return Result.ok();
    }

    /**
     * 根据spuId获取销售属性
     */
    @GetMapping("spuSaleAttrList/{spuId}")
    private Result  spuSaleAttrList(@PathVariable Long spuId){
        List<SpuSale> spuSales = spuService.spuSaleAttrList(spuId);
        return Result.ok(spuSales);
    }

    /**
     * 根据spuId获取销售属性
     */
    @GetMapping("spuImageList/{spuId}")
    private Result  spuImageList(@PathVariable Long spuId){
        List<SpuImage> spuImages = spuService.spuImageList(spuId);
        return Result.ok(spuImages);
    }

}
