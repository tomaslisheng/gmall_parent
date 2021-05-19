package com.atguigu.gmall.beans;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * author lisheng
 * Date:2021/5/15
 * Description:
 */
@Data
public class BaseEntity implements Serializable {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
}
