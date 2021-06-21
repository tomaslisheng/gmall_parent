package com.atguigu.gmall.service.impl;

import com.atguigu.gmall.cart.CartInfo;
import com.atguigu.gmall.client.CartFeignClient;
import com.atguigu.gmall.client.UserFeignClient;
import com.atguigu.gmall.enums.OrderStatus;
import com.atguigu.gmall.mapper.OrderDetailMapper;
import com.atguigu.gmall.mapper.OrderMapper;
import com.atguigu.gmall.order.OrderDetail;
import com.atguigu.gmall.order.OrderInfo;
import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.user.UserAddress;
import com.atguigu.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * author lisheng
 * Date:2021/6/7
 * Description:
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CartFeignClient cartFeignClient;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public OrderInfo getOrderView(String userId) {
        List<CartInfo> cartInfos = cartFeignClient.getCartChecked(userId);
        List<UserAddress> addresss = userFeignClient.getUserAddress(userId);
        //订单总金额
        BigDecimal totalAmount = new BigDecimal(0);
        Integer totalNum =0;
        OrderInfo orderInfo = new OrderInfo();
        List<OrderDetail> orderDetails = new ArrayList<>();
        if(cartInfos.size()>0){
            for (CartInfo cartInfo : cartInfos) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setSkuId(cartInfo.getSkuId());
                orderDetail.setSkuName(cartInfo.getSkuName());
                orderDetail.setSkuNum(cartInfo.getSkuNum());
                orderDetail.setImgUrl(cartInfo.getImgUrl());
                orderDetail.setOrderPrice(cartInfo.getCartPrice());
                totalAmount = totalAmount.add(BigDecimal.valueOf(orderDetail.getSkuNum()).multiply(orderDetail.getOrderPrice()));
                totalNum = totalNum+cartInfo.getSkuNum();
                orderDetails.add(orderDetail);
            }
            orderInfo.setImgUrl(orderDetails.get(0).getImgUrl());
            orderInfo.setOrderComment(orderDetails.get(0).getSkuName());
        }
        //查询当前用户所有收货信息
        orderInfo.setDeliveryAddress(addresss.get(0).getUserAddress());
        orderInfo.setConsigneeTel(addresss.get(0).getPhoneNum());
        orderInfo.setConsignee(addresss.get(0).getConsignee());
        orderInfo.setCreateTime(new Date());
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setOrderDetailList(orderDetails);
        orderInfo.setUserAddresses(addresss);
        orderInfo.setTotalNum(totalNum);
        String  outTradeNo = "zhifubao"+System.currentTimeMillis()+ new SimpleDateFormat("yyyyMMddmmsss").format(new Date());
        orderInfo.setOutTradeNo(outTradeNo);
        return orderInfo;
    }

    @Override
    public OrderInfo submitOrder(String userId, String tradeNo, OrderInfo order) {
        OrderInfo orderinfo = getOrderView(userId);
        //获取过期时间。超时未支付需要取消订单，释放库存
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_MONTH,1);
        Date expireTime = instance.getTime();
        orderinfo.setExpireTime(expireTime);
        orderMapper.insert(orderinfo);
        orderinfo.setOrderStatus(OrderStatus.UNPAID.toString());

        List<OrderDetail> orderDetailList = orderinfo.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList) {
            orderDetail.setOrderId(Long.valueOf(orderinfo.getId()));
            orderDetailMapper.insert(orderDetail);
            //创建订单之后，需要将购物车已经下单的商品，清空
            redisTemplate.opsForHash().delete("user:cart:"+userId,"skuId:"+String.valueOf(orderDetail.getSkuId()));
        }


        return orderinfo;
    }

    /**
     * 销毁订单唯一码
     * @param userId
     * @param tradeNo
     */
    @Override
    public void cleanTradeNo(String userId, String tradeNo) {
        redisTemplate.delete("tradeNo:userId:"+userId);
    }

    /**
     *
     *获取订单唯一码
     * @param userId
     * @return
     */
    @Override
    public String getTradeNo(String userId) {
        Object o = redisTemplate.opsForValue().get("tradeNo:userId:" + userId);
        if(o!=null){
            return String.valueOf(o);
        }else{
            return "";
        }
    }

    @Override
    public OrderInfo getOrder(String orderId) {
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        OrderInfo orderInfo = orderMapper.selectById(orderId);
        List<OrderDetail> orderDetails = orderDetailMapper.selectList(queryWrapper);
        orderInfo.setOrderDetailList(orderDetails);
        return orderInfo;
    }

    /**
     * 更改支付状态为已支付
     * @param outTradeNo
     * @return
     */
    @Override
    public Result updateOrder(String outTradeNo) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("out_trade_no",outTradeNo);
        OrderInfo orderInfo = orderMapper.selectOne(queryWrapper);
        orderInfo.setOrderStatus(OrderStatus.PAID.toString());
        orderMapper.update(orderInfo,queryWrapper);
        return Result.ok();
    }
}
