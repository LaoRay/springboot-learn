package com.zhengtoon.framework.web.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * Retry配置类
 *
 * @author LuCheng.Qi
 * @since 2018-07-18
 * Company:北京思源政务通有限公司
 */
@EnableRetry
@Configuration
@EnableConfigurationProperties(OkHttp3Properties.class)
public class RetryConfig {

	@Bean
	@ConditionalOnMissingBean
	public RetryTemplate createRetryTemplate(OkHttp3Properties okHttp3Properties){
		RetryTemplate template = new RetryTemplate();
		SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
		simpleRetryPolicy.setMaxAttempts(okHttp3Properties.getMaxRetry());
		template.setRetryPolicy(simpleRetryPolicy);
		return template;
	}

}
