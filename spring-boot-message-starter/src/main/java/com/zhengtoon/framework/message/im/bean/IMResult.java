package com.zhengtoon.framework.message.im.bean;

import lombok.Data;
import lombok.ToString;


/**
 * 返回消息格式
 * @author LuCheng.Qi
 * @since 2018-08-03
 * Company:北京思源政务通有限公司
 */
@Data
@ToString
public class IMResult {
	private String  msgid;
	private String  seqid;
	private String  from;
	private String  to;
}
