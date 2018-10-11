package com.zhengtoon.framework.jwt;

import com.zhengtoon.framework.jwt.config.JwtProperteis;
import com.zhengtoon.framework.jwt.interceptor.JwtInterceptor;
import com.zhengtoon.framework.jwt.security.TokenHelper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
@EnableConfigurationProperties(JwtProperteis.class)
public class JwtConfig {

	@Bean
	public JwtInterceptor jwtInterceptor(TokenHelper tokenHelper){
		return new JwtInterceptor(tokenHelper);
	}

	@Bean
	public TokenHelper tokenHelper(JwtProperteis jwtProperteis){
		return new TokenHelper(jwtProperteis);
	}

}
