package com.zhengtoon.framework.message.im.bean.enums;


import lombok.Getter;

/**
 * IM msg type
 * @author LuCheng.Qi
 * @since 2018-08-03
 * Company:北京思源政务通有限公司
 */
public enum MsgTypeEnum {

	//单聊
	SINGLECHAT("52"),
	//群聊
	GROUPCHAT("53"),
	//个人通知
	SINGLENOTICE("51"),
	//群通知
	GROUPNOTICE("50");

	@Getter
	private String type;

	MsgTypeEnum(String type) {
		this.type = type;
	}

}
