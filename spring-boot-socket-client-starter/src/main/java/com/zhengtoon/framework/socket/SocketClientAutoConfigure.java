package com.zhengtoon.framework.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
@EnableConfigurationProperties(SocketProperties.class)
public class SocketClientAutoConfigure {

	@Bean
	public ServerRunner createServerRunner() {
		return new ServerRunner();
	}

}
