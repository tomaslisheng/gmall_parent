package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.cart.CartInfo;
import com.atguigu.gmall.client.ProductFeignClient;
import com.atguigu.gmall.mapper.CartMapper;
import com.atguigu.gmall.product.SkuInfo;
import com.atguigu.gmall.service.CartService;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author lisheng
 * Date:2021/6/1
 * Description:
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public SkuInfo addCart(Long skuId, Long skuNum,String userId,String userTempId) {
        String trueUserId = "";
        if(StringUtils.isEmpty(userId)){
            trueUserId=userTempId;
        }else{
            trueUserId = userId;
        }
        SkuInfo skuInfo = productFeignClient.getProduct(skuId);
        CartInfo cartInfo = new CartInfo();
                //加入购物车
        //1,先加入redis
        // TODO: 2021/6/1  用户不确定 暂时默认为1
        CartInfo cartInfoRedis = (CartInfo)redisTemplate.opsForHash().get("user:cart:"+trueUserId, "skuId:" + skuId);
        if(cartInfoRedis==null){
            //如果redis 不存在当前商品购物车信息，则 先写入CartInfo 然后同步到redis

            cartInfo.setImgUrl(skuInfo.getSkuDefaultImg());
            cartInfo.setIsChecked(0);
            cartInfo.setSkuId(skuId);
            cartInfo.setSkuName(skuInfo.getSkuName());
            cartInfo.setSkuPrice(BigDecimal.valueOf(skuInfo.getPrice()));
            cartInfo.setSkuNum(skuNum.intValue());
            cartInfo.setCartPrice(BigDecimal.valueOf(skuInfo.getPrice()).multiply(BigDecimal.valueOf(skuNum)));
            cartMapper.insert(cartInfo);

            //缓存至 redis
            redisTemplate.opsForHash().put("user:cart:"+trueUserId, "skuId:" + skuId,cartInfo);
        }
        else{
            cartInfoRedis.setSkuNum(cartInfoRedis.getSkuNum()+skuNum.intValue());
            cartMapper.updateById(cartInfoRedis);

            //缓存至 redis
            redisTemplate.opsForHash().put("user:cart:"+trueUserId, "skuId:" + skuId,cartInfoRedis);
        }
        //2，MySQL备份，查看Mysql是否存在。如果存在直接直接修改数量。否则新增
        return skuInfo;
    }

    @Override
    public List<CartInfo> cartList(String userId,String userTempId) {
         List<CartInfo> cartInfos = new ArrayList<>();
        if(StringUtils.isEmpty(userId)){
            cartInfos = redisTemplate.opsForHash().values("user:cart:"+userTempId);
        }
        else{
            cartInfos = redisTemplate.opsForHash().values("user:cart:"+userId);
        }
        return cartInfos;
    }


    @Override
    public List<CartInfo> getCartChecked(String userId) {
        List<CartInfo> cartInfos = new ArrayList<>();
        List<CartInfo> cartInfosRedis = new ArrayList<>();
        cartInfosRedis = redisTemplate.opsForHash().values("user:cart:"+userId);

        //筛选出选中的商品
        Map<Integer, List<CartInfo>> collect = cartInfosRedis.stream().collect(Collectors.groupingBy(CartInfo::getIsChecked));
        for (Map.Entry<Integer, List<CartInfo>> map : collect.entrySet()) {
            if(1==map.getKey()){
                cartInfos = map.getValue();
            }
        }
        System.out.println(collect);

        return cartInfos;
    }

    @Override
    public void checkCart(Long skuId, Long isChecked,String userId,String userTempId) {
        CartInfo cartInfoRedis =null;
        //更新redis
        if(StringUtils.isEmpty(userId)){
             cartInfoRedis = (CartInfo)redisTemplate.opsForHash().get("user:cart:"+userTempId, "skuId:" + skuId);
            cartInfoRedis.setIsChecked(isChecked.intValue());
            redisTemplate.opsForHash().put("user:cart:"+userTempId, "skuId:" + skuId,cartInfoRedis);
        }
        else{
             cartInfoRedis = (CartInfo)redisTemplate.opsForHash().get("user:cart:"+userId, "skuId:" + skuId);
            cartInfoRedis.setIsChecked(isChecked.intValue());
            redisTemplate.opsForHash().put("user:cart:"+userId, "skuId:" + skuId,cartInfoRedis);
        }


        //更新mysql
        cartMapper.updateById(cartInfoRedis);
    }

    @Override
    public void deleteCart(Long skuId,String userId,String userTempId) {
        CartInfo cartInfoRedis =null;
        //redis 获取cartInfo信息
        if(StringUtils.isEmpty(userId)){
            cartInfoRedis = (CartInfo)redisTemplate.opsForHash().get("user:cart:"+userTempId, "skuId:" + skuId);
            //移除
            redisTemplate.opsForHash().delete("user:cart:"+userTempId, "skuId:" + skuId);
        }
        else{
            cartInfoRedis = (CartInfo)redisTemplate.opsForHash().get("user:cart:"+userId, "skuId:" + skuId);
            //移除
            redisTemplate.opsForHash().delete("user:cart:"+userId, "skuId:" + skuId);
        }



        //mysql删除
        cartMapper.deleteById(cartInfoRedis.getId());
    }

    @Override
    public void addToCart(Long skuId, Long skuNum,String userId,String userTempId) {
        //更新redis
        CartInfo cartInfoRedis = new CartInfo();
        if(StringUtils.isEmpty(userId)){
             cartInfoRedis = (CartInfo)redisTemplate.opsForHash().get("user:cart:"+userTempId, "skuId:" + skuId);
            cartInfoRedis.setSkuNum(skuNum.intValue());
            redisTemplate.opsForHash().put("user:cart:"+userTempId, "skuId:" + skuId,cartInfoRedis);
        }
        else{
             cartInfoRedis = (CartInfo)redisTemplate.opsForHash().get("user:cart:"+userId, "skuId:" + skuId);
            cartInfoRedis.setSkuNum(skuNum.intValue());
            redisTemplate.opsForHash().put("user:cart:"+userId, "skuId:" + skuId,cartInfoRedis);
        }


        //更新mysql
        cartMapper.updateById(cartInfoRedis);
    }
}
