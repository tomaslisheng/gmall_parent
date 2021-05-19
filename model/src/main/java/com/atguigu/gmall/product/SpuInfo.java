package com.atguigu.gmall.product;

import com.atguigu.gmall.beans.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Data
@ApiModel("SPU信息")
public class SpuInfo extends BaseEntity {
    @ApiModelProperty("spu名称")
    @TableField("spu_name")
    private String spuName;
    @ApiModelProperty("SPU说明")
    @TableField("description")
    private String description;
    @ApiModelProperty("SPU所属三级分类")
    @TableField("category3_id")
    private String category3Id;
    @ApiModelProperty("品牌ID")
    @TableField("tm_id")
    private String tmId;
    @TableField(exist = false)
    private List<SpuImage> spuImageList;
    @TableField(exist = false)
    private List<SpuSale> spuSaleAttrList;

}
