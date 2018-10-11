package com.zhengtoon.framework.jwt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperteis {

	/**
	 * 是否使用uias认证
	 */
	public Boolean isUserUias = Boolean.TRUE;

	/**
	 * 签发名称
	 */
	private String name = "zhengtoon";

	/**
	 * 密钥
	 */
	public String secret;

	/**
	 * 过期时间，单位分钟
	 */
	private int expires = 30;
}
