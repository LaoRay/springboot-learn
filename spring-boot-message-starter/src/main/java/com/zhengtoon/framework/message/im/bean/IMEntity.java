package com.zhengtoon.framework.message.im.bean;

import com.alibaba.fastjson.JSON;
import com.zhengtoon.framework.message.im.bean.enums.MsgTypeEnum;
import lombok.Data;
import lombok.ToString;


/**
 *  im消息实体
 * 属性详细解释见：http://wiki.syswin.com/display/TOONMANUAL/%5BOpen%5D+sendmsg
 * @author LuCheng.Qi
 * @since 2018-08-03
 * Company:北京思源政务通有限公司
 */
@Data
@ToString
public class IMEntity {
	/*
	 * 消息id
	 */
	private String msgid;
	/*
	 * 消息发送者的名片的feedID或者appid
	 */
	private String from;
	/*
	 * 消息接收者的名片的feedID或者群组ID
	 */
	private String to;
	/*
	 * 接收消息的用户的UserID（发一对一消息必须填写此字段）
	 */
	private String toClient;

	/*
	 * 推送信息
	 */
	private String pushInfo;

	/*
	 * 消息类型，默认为通知类型
	 */
	private String msgType;

	/*
	 * 消息内容
	 */
	private Content content = new Content();

	/*
	 * 消息优先级
	 */
	private Integer priority = 1;

	/*
	 * 接收者的通app类型
	 */
	private String to_toon_type;

	/*
	 * 应用ID
	 */
	private String appid;

	public void setMsgType(MsgTypeEnum msgTypeEnum) {
		this.msgType = msgTypeEnum.getType();
	}

	@Data
	@ToString
	public static class Content {
		//业务类型
		private Integer catalogId;
		private String subCatalog;
		private String subCatalogId;
		private Integer headFlag = 0;
		private String headFeed;
		private String bizNo;
		//20（富文本通知消息）
		private Integer contentType = 20;
		private String content;

		public void setContent(ContentObject ContentObject) {
			this.content = JSON.toJSONString(ContentObject);
		}
	}


}
