package com.atguigu.gmall.beans;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * author lisheng
 * Date:2021/5/15
 * Description:
 */
public class BaseEntity implements Serializable {
    @TableId
    private String id;
}
