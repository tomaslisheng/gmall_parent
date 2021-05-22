package com.atguigu.gmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.mapper.CategoryViewMapper;
import com.atguigu.gmall.mapper.SkuImageMapper;
import com.atguigu.gmall.mapper.SkuMapper;
import com.atguigu.gmall.mapper.SpuSaleMapper;
import com.atguigu.gmall.product.CategoryView;
import com.atguigu.gmall.product.SkuImage;
import com.atguigu.gmall.product.SkuInfo;
import com.atguigu.gmall.product.SpuSale;
import com.atguigu.gmall.service.CateGoryService;
import com.atguigu.gmall.service.ProductService;
import com.atguigu.gmall.service.SkuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author lisheng
 * Date:2021/5/19
 * Description:
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private SkuService skuService;
    @Autowired
    private CateGoryService cateGoryService;
    @Autowired
    private SkuImageMapper skuImageMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SpuSaleMapper spuSaleMapper;
    @Autowired
    private CategoryViewMapper categoryViewMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Map<String, Object> getProductDetail(Long skuId) throws InterruptedException {
        HashMap<String,Object> map = new HashMap<>();
        System.out.println(redisTemplate);
        //查询SKU基础信息
        //使用redis缓存，减轻MySQL数据库压力
        SkuInfo skuInfo = (SkuInfo) redisTemplate.opsForValue().get("sku:" + skuId + "info");
        //如果缓存中不存在当前数据，则从MySQL库中查询
        if(skuInfo == null){
            //由于考虑并发情况，需要添加reids分布式锁，来实现限流的作用
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("sku:" + skuId + "lock", "1");
            //拿到锁，执行查询操作。否则等待
            if(aBoolean){
                skuInfo = skuMapper.selectById(skuId);
                //查询之后缓存至redis中
                redisTemplate.opsForValue().set("sku:" + skuId + "+info",skuInfo);
            }
            else {
                //如果没有获取锁，则等待3秒，重新查询
                Thread.sleep(3000);
                return  getProductDetail(skuId);
            }

        }

        //查询多级菜单信息
        QueryWrapper<CategoryView> wrapper = new QueryWrapper<>();
        wrapper.eq("category3Id",skuInfo.getCategory3Id());
        CategoryView categoryView = categoryViewMapper.selectOne(wrapper);
        map.put("categoryView",categoryView);
        //查询所有SKU图片列表
        QueryWrapper<SkuImage> skuWrapper = new  QueryWrapper<>();
        skuWrapper.eq("sku_id",skuId);
        List<SkuImage> skuImages = skuImageMapper.selectList(skuWrapper);
        //查询价格信息（由于价格比较敏感。为了强一致性。从MySQL单独查询。）
        //map.put("skuImageList",skuImages);
        //图片信息写入skuInfo
        skuInfo.setSkuImageList(skuImages);
        map.put("skuInfo",skuInfo);
        //写入价格信息
        map.put("price",skuInfo.getPrice());
        //查询销售属性信息
        List<SpuSale> sales = spuSaleMapper.getSaleProductId(skuInfo.getSpuId(),skuId);
        System.out.println(sales);
        map.put("spuSaleAttrList",sales);

        //显示所有 sku对应的 销售索性，所有的组合。如何视频。切换产品Id
        List<Map<String,Object>> maps = skuMapper.selectSkuBySpuId(skuInfo.getSpuId());
        //进行遍历
        Map<String,Object> productMaps = new HashMap<>();
        if(maps.size()>0){
            for (Map<String, Object> product : maps) {
                String attrValueIds = (String)product.get("AttrValueIds");
                Long skuIdGroup = (Long) product.get("sku_id");
                productMaps.put(attrValueIds,skuIdGroup);
            }
        }
        String valuesSkuJson = JSON.toJSONString(productMaps);
        map.put("valuesSkuJson",valuesSkuJson);
        System.out.println("*****************valuesSkuJson:"+valuesSkuJson);
        return map;
    }
}
