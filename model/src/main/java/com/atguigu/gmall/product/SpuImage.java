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
@TableName("spu_image")
public class SpuImage extends BaseEntity {
    @TableField("spu_id")
    private Long spuId;
    @TableField("img_name")
    private String imgName;
    @TableField("img_url")
    private String imgUrl;

}
