package com.zhengtoon.framework.demo.web.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 系统启动成功以后的listener监听
 */
@Component
@Slf4j
public class ServerStartupListener implements ApplicationListener<ApplicationReadyEvent> {



	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

	}
}
