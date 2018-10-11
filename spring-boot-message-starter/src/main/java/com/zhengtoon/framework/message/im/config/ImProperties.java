package com.zhengtoon.framework.message.im.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Im的配置信息
 * @author LuCheng.Qi
 * @since 2018-08-03
 * Company:北京思源政务通有限公司
 */
@Data
@ConfigurationProperties(prefix = "toon.im")
public class ImProperties {
	//消息服务地址
	private String serverUrl;
	//应用id
	private String appId;
	//toon类型
	private String toonType;
	//消息业务类型，默认142为业务
	private Integer catalogId = 142;
}
