package com.zhengtoon.framework.message;

import com.zhengtoon.framework.message.im.config.ImProperties;
import com.zhengtoon.framework.message.im.service.ToonIMService;
import com.zhengtoon.framework.message.im.service.impl.ToonIMServiceImpl;
import com.zhengtoon.framework.web.WebConfig;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * message组件
 * @author LuCheng.Qi
 * @since 2018-08-03
 * Company:北京思源政务通有限公司
 */
@Configuration
@EnableConfigurationProperties(ImProperties.class)
@ComponentScan(basePackageClasses = {MessageConfig.class, WebConfig.class})
public class MessageConfig {

	/**
	 * 创建IM访问对象
	 * @param okHttpClient http客户端
	 * @param imProperties 配置信息
	 * @return IM访问对象
	 */
	@Bean
	public ToonIMService toonIMService(OkHttpClient okHttpClient , ImProperties imProperties) {
		return new ToonIMServiceImpl(okHttpClient, imProperties);
	}

}
