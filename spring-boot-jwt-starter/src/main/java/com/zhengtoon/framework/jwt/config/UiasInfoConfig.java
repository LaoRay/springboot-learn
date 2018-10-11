package com.zhengtoon.framework.jwt.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 统一身份认证信息配置
 */
@Data
@Component
@ToString
public class UiasInfoConfig {

	/**
	 * 获取AccessToken的URL
	 */
	@Value("${uias.info.accessTokenUrl:}")
	private String accessTokenUrl;

	/**
	 * 获取UserInfo的URL
	 */
	@Value("${uias.info.userInfoUrl:}")
	private String userInfoUrl;

	/**
	 * appId
	 */
	@Value("${uias.info.clientId:}")
	private String clientId;

	/**
	 * appSecret
	 */
	@Value("${uias.info.secretKey:}")
	private String secretKey;

	/**
	 * 应用回调地址URL
	 */
	@Value("${uias.info.redirectUrl:}")
	private String redirectUrl;

	/**
	 * 授权类别
	 */
	private String grantType;

	/**
	 * session 超时时间
	 */
	@Value("${uias.info.sessionTimeOut:30}")
	private Integer sessionTimeOut;

}
