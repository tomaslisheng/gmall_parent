package com.atguigu.gmall.product;

import com.atguigu.gmall.beans.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Data
@TableName("sku_attr_value")
@ApiModel("SKU销售属性")
public class SkuSale extends BaseEntity {
    @TableField("sku_id")
    private Long skuId;
    @TableField("attr_id")
    private String attrId;
    @TableField("value_id")
    private String valueId;

}
