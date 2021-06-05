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
@TableName("base_category_view")
public class CategoryView {
    @TableField("category1_id")
    private String category1Id;
    @TableField("category1_name")
    private String category1Name;
    @TableField("category2_id")
    private String category2Id;
    @TableField("category2_name")
    private String category2Name;
    @TableField("category3_id")
    private String category3Id;
    @TableField("category3_name")
    private String category3Name;
}
