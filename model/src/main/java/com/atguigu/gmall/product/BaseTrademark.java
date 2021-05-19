package com.atguigu.gmall.product;

import com.atguigu.gmall.beans.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * author lisheng
 * Date:2021/5/17
 * Description:
 */
@Data
@ApiModel("品牌表")
@TableName("base_trademark")
public class BaseTrademark extends BaseEntity {
    @ApiModelProperty("品牌名称")
    @TableField("tm_name")
    private String tmName;

    @ApiModelProperty("品牌图标")
    @TableField("logo_url")
    private String logoUrl;

}
