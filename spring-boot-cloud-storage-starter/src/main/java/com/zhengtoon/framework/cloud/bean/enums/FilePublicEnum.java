package com.zhengtoon.framework.cloud.bean.enums;

import lombok.Getter;

/**
 * 文件类型
 */
public enum FilePublicEnum {

	/**
	 * 公开
	 */
	PUBLIC("1");

	@Getter
	private String value;

	FilePublicEnum(String value) {
		this.value = value;
	}
}
