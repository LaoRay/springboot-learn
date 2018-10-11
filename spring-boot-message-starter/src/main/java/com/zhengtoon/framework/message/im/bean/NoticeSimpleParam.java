package com.zhengtoon.framework.message.im.bean;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

/**
 * 发送通知消息的简化参数
 * @author LuCheng.Qi
 * @since 2018-08-03
 * Company:北京思源政务通有限公司
 */
@Data
@Builder
@ToString
public class NoticeSimpleParam {
	//消息id
	UUID msgID;
	//业务id
	UUID bizNo;
	//接受方Id
	String toClient;
	//推送消息标题
	String pushInfo;
	//正文标题
	String title;
	//正文内容
	String content;
	//正文图片地址
	String imgUrl;
	//正文图片点击跳转url
	String imgRedictUrl;
	String imgDefaulttUrl;
	//下方按钮文字
	String btnContent;
	//下方按钮点击跳转url
	String btnUrl;
	String btnDefaultUrl;
}
