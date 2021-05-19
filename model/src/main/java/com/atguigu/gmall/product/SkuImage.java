package com.atguigu.gmall.product;

import com.atguigu.gmall.beans.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Data
@TableName("sku_image")
public class SkuImage extends BaseEntity {
    @TableField("sku_id")
    private Long skuId;
    @TableField("img_name")
    private String imgName;
    @TableField("img_url")
    private String imgUrl;
    @TableField("spu_img_id")
    private String spuImgId;
    @TableField("is_default")
    private String isDefault;

}
