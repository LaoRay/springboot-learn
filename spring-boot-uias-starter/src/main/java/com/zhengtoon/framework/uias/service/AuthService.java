package com.zhengtoon.framework.uias.service;


/**
 * 统一身份认证接口
 */
public interface AuthService {

	/**
	 * 获取userInfo信息
	 * @param accessToken
	 * @return
	 */
	String getUserInfo(String accessToken);

	/**
	 * 获取acessToken
	 * @param code
	 * @return
	 */
	String getAccessToken(String code);
}
