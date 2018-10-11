package com.zhengtoon.framework.message.im.bean;


import com.zhengtoon.framework.message.im.bean.enums.ColorEnums;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 消息描述对象,包含IM接口能提供的所有参数
 * 如果工厂类提供的参数不满足调用需求，也可以自己构造本对象
 * @author LuCheng.Qi
 * @since 2018-08-03
 * Company:北京思源政务通有限公司
 */
@Data
@ToString
public class ContentObject {

	private List<ContentDetail> contentDetail;
	private List<ImageDetail> imageDetail;
	private List<HandleButton> handleButton;
	private List<MsgEnty> msgEnty;
	private List<MsgUrl> msgUrl;
	private List<MsgStatus> msgStatus;

	/**
	 * 消息内容对象
	 */
	@Data
	@ToString
	public static class ContentDetail {
		//0代表正文
		private String type = "0";
		private String content;
		private String color = ColorEnums.black.getValue();
		private String font;
		private String bold;
		private String url;
		private String defaultUrl;
	}

	/**
	 * 消息条目对象
	 */
	@Data
	@ToString
	public static class MsgEnty {
		private String content;
		private String color = ColorEnums.black.getValue();
		private String font;
		private String url;
		private String defaultUrl;
		private String iconUrl;
	}

	@Data
	@ToString
	public static class ImageDetail {
		//图标的url
		private String iconUrl;
		//图标点击跳转url
		private String url;
		private String defaultUrl;
	}

	@Data
	@ToString
	public static class HandleButton {
		private String content;
		private String color = ColorEnums.black.getValue();
		private String font;
		private String url;
		private String defaultUrl;
	}

	@Data
	@ToString
	public static class MsgUrl {
		private String content;
		private String color = ColorEnums.black.getValue();
		private String font;
		private String bold;
		private String url;
		private String defaultUrl;
	}

	@Data
	@ToString
	public static class MsgStatus {
		private String content;
		private String iconUrl;
		private String color = ColorEnums.black.getValue();
		private String font;
		private String bold;
	}

}
