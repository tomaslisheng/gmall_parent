package com.atguigu.gmall.controller;

import com.atguigu.gmall.product.AttrInfo;
import com.atguigu.gmall.product.AttrValue;
import com.atguigu.gmall.service.AttrInfoService;
import com.atguigu.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@RestController
@RequestMapping("/admin/product")
@CrossOrigin
public class AttrInfoApiController {

    @Autowired
    private AttrInfoService attrInfoService;
    @RequestMapping("attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result attrInfoList(@PathVariable String category1Id,@PathVariable String category2Id,@PathVariable String category3Id){
        List<AttrInfo> attrInfoList =  attrInfoService.attrInfoList(category1Id,category2Id,category3Id);
        return  Result.ok(attrInfoList);
    }
    @PostMapping("saveAttrInfo")
    public Result saveAttrInfo(@RequestBody AttrInfo attrInfo){
        attrInfoService.saveAttrInfo(attrInfo);
        return  Result.ok();
    }
    @GetMapping("getAttrValueList/{attrId}")
    public Result getAttrValueList(@PathVariable Long attrId){
        //AttrInfo attrInfo = attrInfoService.getAttrValueList(attrId);
        List<AttrValue> attrValues = attrInfoService.getAttrValueLists(attrId);
        return  Result.ok(attrValues);
    }


}
