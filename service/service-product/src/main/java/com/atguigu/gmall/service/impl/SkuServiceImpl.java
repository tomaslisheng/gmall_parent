package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.mapper.SkuImageMapper;
import com.atguigu.gmall.mapper.SkuMapper;
import com.atguigu.gmall.mapper.SkuSaleAttrValueMapper;
import com.atguigu.gmall.mapper.SkuSaleMapper;
import com.atguigu.gmall.product.*;
import com.atguigu.gmall.service.SkuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SkuImageMapper skuImageMapper;
    @Autowired
    private SkuSaleMapper skuSaleMapper;
    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Override
    public IPage<SkuInfo> listPage(Long page, Long limit) {
        Page<SkuInfo> page1 = new Page<SkuInfo>(page,limit);
        IPage<SkuInfo> skuInfoIPage = skuMapper.selectPage(page1, null);
        return skuInfoIPage;
    }

    @Override
    public void onSale(Long skuId) {
        QueryWrapper wrapper = new QueryWrapper();
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setIsSale("1");
        wrapper.eq("id",skuId);
        skuMapper.update(skuInfo,wrapper);
    }

    @Override
    public void cancelSale(Long skuId) {
        QueryWrapper wrapper = new QueryWrapper();
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setIsSale("0");
        wrapper.eq("id",skuId);
        skuMapper.update(skuInfo,wrapper);
    }

    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        //保存 SKU基础信息
        skuMapper.insert(skuInfo);
        //保存SKU图片信息
        String isDefaultImage = "";
        for (SkuImage skuImage : skuInfo.getSkuImageList()) {
            skuImage.setSkuId(skuInfo.getId());
            skuImageMapper.insert(skuImage);
            if("1".equals(skuImage.getIsDefault())){
                isDefaultImage = skuImage.getImgUrl();
                skuInfo.setSkuDefaultImg(isDefaultImage);
                skuMapper.updateById(skuInfo);
            }
        }
        //保存SKU 销售信息
        for (SkuSale skuSale : skuInfo.getSkuAttrValueList()) {
            skuSale.setSkuId(skuInfo.getId());
            skuSaleMapper.insert(skuSale);
        }
        //保存SKU销售值信息
        for (SkuSaleAttrValue skuSaleAttrValue : skuInfo.getSkuSaleAttrValueList()) {
            skuSaleAttrValue.setSkuId(skuInfo.getId());
            skuSaleAttrValue.setSpuId(skuInfo.getSpuId());
            skuSaleAttrValueMapper.insert(skuSaleAttrValue);
        }
    }
}
