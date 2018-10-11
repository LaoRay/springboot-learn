package com.zhengtoon.framework.email.config;

import com.zhengtoon.framework.email.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @auther: qindaorong
 * @Date: 2018/6/27 09:15
 * @Description:
 */
@Configuration
@ComponentScan({"com.zhengtoon.framework.email.config"})
public class ZhengToonEmailConfiguration {
    private final Logger logger = LoggerFactory.getLogger(ZhengToonEmailConfiguration.class);

    @Value("${spring.mail.host:}")
    private String host;

    @Value("${spring.mail.username:}")
    private String username;

    @Value("${spring.mail.password:}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth:}")
    private Boolean isSmtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable:}")
    private Boolean isSmtpAuthEnable;

    @Value("${spring.mail.properties.mail.smtp.starttls.required:}")
    private Boolean isSmtpAuthRequired;


    @PostConstruct
    public void init(){
        logger.info("ZhengToonEmailConfiguration start init");
        if( StringUtils.isEmpty(host)|| StringUtils.isEmpty(username)|| StringUtils.isEmpty(password)|| StringUtils.isEmpty(isSmtpAuth) || Objects.equals(null ,isSmtpAuth)|| Objects.equals(null ,isSmtpAuthEnable)|| Objects.equals(null ,isSmtpAuthRequired)){
            logger.error("email properties is not find! can not use [spring-boot-email-starter],init over!");
            return;
        }
        logger.info("ZhengToonEmailConfiguration end init");
    }

    @Bean
    @ConditionalOnMissingBean(MailService.class)
    public MailService mailService(){
        MailService mailService = new MailService();
        return mailService;
    }

}
