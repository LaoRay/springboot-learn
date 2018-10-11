package com.zhengtoon.framework.email.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;

/**
 * @auther: qindaorong
 * @Date: 2018/8/1 10:30
 * @Description:
 */
@Configuration
@PropertySource("classpath:/email.properties")
public class JavaMailConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private Environment env;

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix(env.getProperty("spring.thymeleaf.prefix"));
        templateResolver.setSuffix(env.getProperty("spring.thymeleaf.suffix"));
        templateResolver.setTemplateMode(env.getProperty("spring.thymeleaf.mode"));
        templateResolver.setCharacterEncoding(env.getProperty("spring.thymeleaf.encoding"));
        templateResolver.setCacheable(Boolean.valueOf(env.getProperty("spring.thymeleaf.cache")));
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(this.applicationContext == null) {
            this.applicationContext = applicationContext;
        }
    }
}
