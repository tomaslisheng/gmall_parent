package com.atguigu.gmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.aop.GmallCache;
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
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

        //1，查询商品基础信息
        SkuInfo skuInfo = getProduct(skuId);
        CategoryView categoryView = getCategoryView(skuInfo.getCategory3Id());
        //2，查询所有SKU图片列表
        List<SkuImage> skuImages = getSkuImageList(skuInfo.getId());
        //3，查询价格信息（由于价格比较敏感。为了强一致性。从MySQL单独查询。）
        //图片信息写入skuInfo
        skuInfo.setSkuImageList(skuImages);
        //5，查询销售属性信息
        List<SpuSale> sales = getSaleProductId(skuInfo.getSpuId(), skuId);
        //6，显示所有 sku对应的 销售索性，所有的组合。如何视频。切换产品Id
        String valuesSkuJson = getSkuBySpuId(skuInfo.getSpuId());
        skuInfo.setSkuImageList(skuImages);
        map.put("categoryView",categoryView);
        map.put("skuInfo",skuInfo);
        //写入价格信息
        map.put("price",skuInfo.getPrice());
        map.put("valuesSkuJson",valuesSkuJson);
        map.put("spuSaleAttrList",sales);
        return map;
    }

    @GmallCache
    @Override
    public  SkuInfo getProduct(Long skuId) throws InterruptedException {
        //查询SKU基础信息
        //1,使用redis缓存，减轻MySQL数据库压力
        SkuInfo   skuInfo = skuMapper.selectById(skuId);
        return skuInfo;

    }
    //@GmallCache
    public  SkuInfo getProductback(Long skuId) throws InterruptedException {
         //查询SKU基础信息
        //1,使用redis缓存，减轻MySQL数据库压力
        SkuInfo skuInfo = (SkuInfo) redisTemplate.opsForValue().get("sku:" + skuId + "info");
        //如果缓存中不存在当前数据，则从MySQL库中查询
        if(skuInfo == null){
            String lockTag = UUID.randomUUID().toString();
            //2,由于考虑并发情况，需要添加reids分布式锁，来实现限流的作用
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("sku:" + skuId + "lock", lockTag,1, TimeUnit.SECONDS);
            //拿到锁，执行查询操作。否则等待
            if(aBoolean){
                skuInfo = skuMapper.selectById(skuId);
                //查询之后缓存至redis中
                redisTemplate.opsForValue().set("sku:" + skuId + "+info",skuInfo);
                //3,缓存完成之后关闭锁,关闭锁可能出现的情况，由于服务卡顿或者连接情况。造成线程堵塞，可以设置超时时间
                //4，用用同一把锁，同一个版本号，会造成 A释放B创建的锁的情况。所以，版本号，设置成 可变的。然后判断解锁。
                //redisTemplate.delete("sku:" + skuId + "lock");
                String concurrentTag = (String) redisTemplate.opsForValue().get("sku:" + skuId + "lock");
                if(concurrentTag!=null&&concurrentTag.equals(lockTag)){
                    redisTemplate.delete("sku:" + skuId + "lock");
                }
            }
            else {
                //如果没有获取锁，则等待3秒，重新查询
                Thread.sleep(3000);
                return  getProductback(skuId);
            }

        }
        return skuInfo;

    }
    @GmallCache
    @Override
    public  CategoryView getCategoryView(Long category3Id) {
        //查询多级菜单信息
        QueryWrapper<CategoryView> wrapper = new QueryWrapper<>();
        wrapper.eq("category3Id",category3Id);
        CategoryView categoryView = categoryViewMapper.selectOne(wrapper);
        return categoryView;
    }
    @GmallCache
    @Override
        public  List<SkuImage> getSkuImageList(Long skuId) {
        //查询所有SKU图片列表
        QueryWrapper<SkuImage> skuWrapper = new  QueryWrapper<>();
        skuWrapper.eq("sku_id",skuId);
        List<SkuImage> skuImages = skuImageMapper.selectList(skuWrapper);
        return skuImages;
    }
    @GmallCache
    @Override
    public  List<SpuSale> getSaleProductId(Long spuId,Long skuId){
        //查询销售属性信息
        List<SpuSale> sales = spuSaleMapper.getSaleProductId(spuId,skuId);
       return sales;
    }
    @GmallCache
    @Override
    public  String getSkuBySpuId(Long spuId){
        //显示所有 sku对应的 销售索性，所有的组合。如何视频。切换产品Id
        List<Map<String,Object>> maps = skuMapper.selectSkuBySpuId(spuId);
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
        return valuesSkuJson;
    }
}
