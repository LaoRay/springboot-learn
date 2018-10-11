package zhengtoon.framework.task.core.config;

import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @auther: qindaorong
 * @Date: 2018/7/16 17:10
 * @Description:
 */
@Configuration
@EnableAutoConfiguration
public class QuartzConfig {
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {
        return new SchedulerFactoryBean();
    }

    @Bean
    public Scheduler scheduler(ApplicationContext applicationContext) {
        return schedulerFactoryBean(applicationContext).getObject();
    }
}
