package com.zhengtoon.framework.demo;

import com.zhengtoon.framework.disconf.EnableDisconfProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationPid;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


@Slf4j
@SpringBootApplication
@EnableDisconfProperties
@MapperScan("com.zhengtoon.*.mapper")
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder()
				.sources(DemoApplication.class)
				.main(DemoApplication.class)
				.run(args);
		log.info("----DemoApplication Start PID={}----", new ApplicationPid().toString());
		context.registerShutdownHook();
	}
}