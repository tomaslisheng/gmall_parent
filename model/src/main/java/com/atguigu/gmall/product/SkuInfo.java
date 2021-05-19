package com.atguigu.gmall.product;

import com.atguigu.gmall.beans.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Data
public class SkuInfo extends BaseEntity {
    @TableField("spu_id")
    private Long spuId;
    @TableField("price")
    private Double price;
    @TableField("sku_name")
    private String skuName;
    @TableField("sku_desc")
    private String skuDesc;
    @TableField("weight")
    private Double weight;
    @TableField("tm_id")
    private Long tmId;
    @TableField("category3_id")
    private Long category3Id;
    @TableField("sku_default_img")
    private String skuDefaultImg;
    @TableField("is_sale")
    private String isSale;
    @TableField(exist = false)
    private List<SkuImage> skuImageList;
    @TableField(exist = false)
    private List<SkuSale> skuAttrValueList;
    @TableField(exist = false)
    private List<SkuSaleAttrValue> skuSaleAttrValueList;
}
