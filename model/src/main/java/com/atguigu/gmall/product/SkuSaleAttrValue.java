package com.atguigu.gmall.product;

import com.atguigu.gmall.beans.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Data
@TableName("sku_sale_attr_value")
@ApiModel("SKU销售属性值表")
public class SkuSaleAttrValue extends BaseEntity {

    @TableField("sku_id")
    private Long skuId;
    @TableField("spu_id")
    private Long spuId;
    @TableField("sale_attr_value_id")
    private String saleAttrValueId;

}
