package zhengtoon.framework.task.manager.starter.config;

import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

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
