package com.zhengtoon.framework.demo.web.interceptor;

import com.zhengtoon.framework.jwt.interceptor.JwtInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

	//@Autowired
	//private SessionInterceptor sessionInterceptor;

	@Autowired
	private JwtInterceptor jwtInterceptor;

	/**
	 * 获取用户session拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(sessionInterceptor).addPathPatterns("/demo/*");
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/demo/*");
		super.addInterceptors(registry);
	}
}
