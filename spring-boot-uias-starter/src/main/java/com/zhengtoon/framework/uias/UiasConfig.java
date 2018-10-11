package com.zhengtoon.framework.uias;

import com.zhengtoon.framework.uias.interceptor.SessionInterceptor;
import com.zhengtoon.framework.uias.service.SessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class UiasConfig {

	@Bean
	public SessionInterceptor sessionInterceptor(SessionService sessionService){
		return new SessionInterceptor(sessionService);
	}
}