package com.atguigu.gmall.product;

import com.atguigu.gmall.beans.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Data
@ApiModel("销售属性表")
@TableName("base_sale_attr")
public class BaseSale extends BaseEntity {
    @TableField("name")
    @ApiModelProperty("销售属性名称")
    private String name;

}
