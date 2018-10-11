package com.zhengtoon.framework.web.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "swagger.info")
public class SwaggerProperties {
	/**
	 * 名称
 	 */
	private String title = "项目接口";
	/**
	 * 描述
 	 */
	private String description = "";
	/**
	 * 版本
 	 */
	private String version = "v1.0";
	/**
	 * 解析的包路径
 	 */
	private String basePackage = "com.zhengtoon";
	/**
	 * 是否启动
 	 */
	private boolean enable = true;
}
