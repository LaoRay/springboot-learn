package com.zhengtoon.framework.message.im.bean.enums;


import lombok.Getter;

/**
 * 颜色枚举
 * @author LuCheng.Qi
 * @since 2018-08-03
 * Company:北京思源政务通有限公司
 */
public enum ColorEnums {
	black("0x000000"),
	green("0x4CD864"),
	blue("0x3395FF"),
	red("0xFF3B2F"),
	gray("0x5C5C5C");
	@Getter
	private String value;
	ColorEnums(String value) {
		this.value = value;
	}
}

