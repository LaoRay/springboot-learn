package com.zhengtoon.framework.web.common;

import com.zhengtoon.framework.entity.ResponseResult;
import com.zhengtoon.framework.exception.BusinessException;
import com.zhengtoon.framework.exception.CoreExceptionCodes;
import lombok.extern.slf4j.Slf4j;

/**
 * Web请求响应处理逻辑模版类
 *
 * @author LuCheng.Qi
 * @since 2018-07-10
 * Company:北京思源政务通有限公司
 */
@Slf4j
public abstract class WebResCallback {

	private final WebResCriteria criteria = new WebResCriteria();

	/**
	 * 处理逻辑
	 *
	 * @param criteria 处理结果
	 * @param params   请求参数
	 */
	public abstract void execute(final WebResCriteria criteria, Object... params);


	/**
	 * 发送请求参数
	 *
	 * @param params 请求参数
	 * @return 返回对象
	 */
	public final ResponseResult sendRequest(Object... params) {
		ResponseResult<Object> webResInfo = new ResponseResult<>();
		try {
			execute(criteria, params);
			webResInfo.setData(criteria.getResult());
			webResInfo.setMeta(CoreExceptionCodes.SUCCESS);
		} catch (BusinessException e) {
			webResInfo = new ResponseResult<>(e.getCode(), e.getMessage());
			log.error("WebResInfo", e);
		} catch (Exception e) {
			webResInfo.setMeta(CoreExceptionCodes.UNKNOWN_ERROR);
			log.error("WebResInfo ", e);
		}
		return webResInfo;
	}

}