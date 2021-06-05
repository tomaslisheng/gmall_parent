package com.atguigu.gmall.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRecode implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long skuId;
	
	private String userId;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
