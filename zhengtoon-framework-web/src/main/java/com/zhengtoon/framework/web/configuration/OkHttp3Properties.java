package com.zhengtoon.framework.web.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * okHttp配置信息
 *
 * @author LuCheng.Qi
 * @since 2018-07-18
 * Company:北京思源政务通有限公司
 */
@Data
@ConfigurationProperties("okhttp3")
public class OkHttp3Properties {

	/**
	 * 连接超时时间
	 */
	private int connectTimeout = 10;

	/**
	 * 读取超时时间
	 */
	private int readTimeout = 10;

	/**
	 * 写超时时间
	 */
	private int writeTimeout = 10;

	/**
	 * okhttp 最大的空闲连接数
	 */
	private int maxIdleConnections = 100;

	/**
	 * 最大重试次数
	 */
	private int maxRetry = 2;

}
