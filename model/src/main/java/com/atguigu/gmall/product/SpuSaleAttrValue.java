package com.atguigu.gmall.product;

import com.atguigu.gmall.beans.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Data
@TableName("spu_sale_attr_value")
@ApiModel("SPU销售属性值表")
public class SpuSaleAttrValue extends BaseEntity {

    @TableField("spu_id")
    private Long spuId;
    @TableField("base_sale_attr_id")
    private Long baseSaleAttrId;
    @TableField("sale_attr_value_name")
    private String saleAttrValueName;
    @TableField("sale_attr_name")
    private String saleAttrName;;
    @TableField(exist = false)
    private String isChecked;
}
