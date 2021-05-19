package com.atguigu.gmall.product;

import com.atguigu.gmall.beans.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Data
@TableName("base_attr_value")
public class AttrValue extends BaseEntity {
    private String valueName;
    private Long attrId;
}
