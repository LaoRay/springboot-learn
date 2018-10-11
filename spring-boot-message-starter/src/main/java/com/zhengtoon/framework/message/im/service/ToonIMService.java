package com.zhengtoon.framework.message.im.service;

import com.zhengtoon.framework.message.im.bean.IMEntity;
import com.zhengtoon.framework.message.im.bean.IMResult;

/**
 * @author LuCheng.Qi
 * @since 2018-08-03
 * Company:北京思源政务通有限公司
 */
public interface ToonIMService {

	/**
	 * 发送消息
	 * @param imEntity 消息实体
	 */
	IMResult sendMessage(IMEntity imEntity);
}
