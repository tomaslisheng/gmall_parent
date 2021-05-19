package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.mapper.*;
import com.atguigu.gmall.product.*;
import com.atguigu.gmall.service.SpuService;
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
public class SpuServiceImpl implements SpuService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private BaseSaleMapper baseSaleMapper;
    @Autowired
    private SpuImageMapper spuImageMapper;
    @Autowired
    private SpuSaleMapper spuSaleMapper;
    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Override
    public IPage<SpuInfo> getSpuPage(Long page, Long limit, Long catgory3Id) {
        IPage<SpuInfo> page1 = new Page<SpuInfo>(page,limit);
        QueryWrapper<SpuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category3_id",catgory3Id);
        IPage<SpuInfo> spuInfoIPage = spuMapper.selectPage(page1, queryWrapper);
        return spuInfoIPage;
    }

    @Override
    public List<BaseSale> baseSaleAttrList() {
        List<BaseSale> sales = baseSaleMapper.selectList(null);
        return sales;
    }

    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        if(spuInfo!=null){
            spuMapper.insert(spuInfo);
            if(spuInfo.getSpuImageList().size()>0){
                for (SpuImage spuImage : spuInfo.getSpuImageList()) {
                    spuImage.setSpuId(spuInfo.getId());
                    spuImageMapper.insert(spuImage);
                }

            }
            if(spuInfo.getSpuSaleAttrList().size()>0){
                for (SpuSale spuSale : spuInfo.getSpuSaleAttrList()) {
                    spuSale.setSpuId(spuInfo.getId());
                    spuSaleMapper.insert(spuSale);
                    for (SpuSaleAttrValue spuSaleAttrValue : spuSale.getSpuSaleAttrValueList()) {
                        spuSaleAttrValue.setBaseSaleAttrId(spuSale.getId());
                        spuSaleAttrValue.setSpuId(spuInfo.getId());
                        spuSaleAttrValue.setSaleAttrValueName(spuSale.getSaleAttrName());
                        spuSaleAttrValueMapper.insert(spuSaleAttrValue);
                    }
                }
            }
        }
    }

    @Override
    public List<SpuSale> spuSaleAttrList(Long spuId) {
        QueryWrapper<SpuSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spu_id",spuId);
        List<SpuSale> spuSales = spuSaleMapper.selectList(queryWrapper);
        for (SpuSale spuSale : spuSales) {
            Long id = spuSale.getId();
            QueryWrapper<SpuSaleAttrValue> wrapper = new QueryWrapper<>();
            wrapper.eq("spu_id",spuId);
            List<SpuSaleAttrValue> spuSaleAttrValues = spuSaleAttrValueMapper.selectList(wrapper);
            spuSale.setSpuSaleAttrValueList(spuSaleAttrValues);
        }
        return spuSales;
    }

    @Override
    public List<SpuImage> spuImageList(Long spuId) {
        QueryWrapper<SpuImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spu_id",spuId);
        List<SpuImage> spuImages = spuImageMapper.selectList(queryWrapper);
        return spuImages;
    }
}
