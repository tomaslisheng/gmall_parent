package com.atguigu.gmall.product;

import com.atguigu.gmall.beans.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * author lisheng
 * Date:2021/5/15
 * Description:
 */
@Data
@ApiModel(value = "分类对象1")
@TableName("base_category3")
public class Category3 extends BaseEntity {
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("category2_id")
    private String  category2Id;
}
