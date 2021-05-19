package com.atguigu.gmall.product;

import com.atguigu.gmall.beans.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@ApiModel(value = "操作属性")
@TableName("base_attr_info")
public class AttrInfo extends BaseEntity {
    @ApiModelProperty("操作属性名")
    private  String attrName;
    @ApiModelProperty("所属三级ID")
    private  Long categoryId;
    @ApiModelProperty("分类级别")
    private String categoryLevel;
    @ApiModelProperty("属性值集合")
    @TableField(exist = false)
    private List<AttrValue> attrValueList;
    @TableField(exist = false)
    private  Long category1Id;
    @TableField(exist = false)
    private  Long category2Id;
    @TableField(exist = false)
    private  Long category3Id;

}
