package com.atguigu.gmall.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * author lisheng
 * Date:2021/5/19
 * Description:
 */
@Data
@TableName("category_view")
public class CategoryView {
    @TableField("category1Id")
    private String category1Id;
    @TableField("category1Name")
    private String category1Name;
    @TableField("category2Id")
    private String category2Id;
    @TableField("category2Name")
    private String category2Name;
    @TableField("category3Id")
    private String category3Id;
    @TableField("category3Name")
    private String category3Name;
}
