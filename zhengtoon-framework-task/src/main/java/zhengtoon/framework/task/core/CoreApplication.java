package zhengtoon.framework.task.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @auther: qindaorong
 * @Date: 2018/7/13 12:11
 * @Description:
 */
@SpringBootApplication
@EnableTransactionManagement
public class CoreApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}


	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(CoreApplication.class);
	}
}