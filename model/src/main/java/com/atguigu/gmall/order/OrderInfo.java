package com.atguigu.gmall.order;

import com.atguigu.gmall.beans.BaseEntity;
import com.atguigu.gmall.user.UserAddress;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "订单信息")
@TableName("order_info")
public class OrderInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收货人")
    @TableField("consignee")
    private String consignee;

    @ApiModelProperty(value = "收件人电话")
    @TableField("consignee_tel")
    private String consigneeTel;

    @ApiModelProperty(value = "总金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "订单状态")
    @TableField("order_status")
    private String orderStatus;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "付款方式")
    @TableField("payment_way")
    private String paymentWay;

    @ApiModelProperty(value = "送货地址")
    @TableField("delivery_address")
    private String deliveryAddress;

    @ApiModelProperty(value = "订单备注")
    @TableField("order_comment")
    private String orderComment;

    @ApiModelProperty(value = "订单交易编号（第三方支付用)")
    @TableField("out_trade_no")
    private String outTradeNo;

    @ApiModelProperty(value = "订单描述(第三方支付用)")
    @TableField("trade_body")
    private String tradeBody;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "失效时间")
    @TableField("expire_time")
    private Date expireTime;

    @ApiModelProperty(value = "进度状态")
    @TableField("process_status")
    private String processStatus;

    @ApiModelProperty(value = "物流单编号")
    @TableField("tracking_no")
    private String trackingNo;

    @ApiModelProperty(value = "父订单编号")
    @TableField("parent_order_id")
    private Long parentOrderId;

    @ApiModelProperty(value = "图片路径")
    @TableField("img_url")
    private String imgUrl;

    @TableField(exist = false)
    private Integer totalNum;
    @TableField(exist = false)
    private List<OrderDetail> orderDetailList;
    @TableField(exist = false)
    private List<UserAddress> userAddresses;

    @TableField(exist = false)
    private String wareId;
    // 计算总价格
    public void sumTotalAmount(){
        BigDecimal totalAmount=new BigDecimal("0");
        for (OrderDetail orderDetail : orderDetailList) {
            totalAmount= totalAmount.add(orderDetail.getOrderPrice().multiply(new BigDecimal(orderDetail.getSkuNum())));
        }
        this.totalAmount=  totalAmount;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeBody() {
        return tradeBody;
    }

    public void setTradeBody(String tradeBody) {
        this.tradeBody = tradeBody;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Long getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(Long parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public String getWareId() {
        return wareId;
    }

    public void setWareId(String wareId) {
        this.wareId = wareId;
    }
}
