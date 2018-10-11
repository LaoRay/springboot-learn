package com.zhengtoon.framework.camel.config;

import com.zhengtoon.framework.camel.CamelRouterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: qindaorong
 * @Date: 2018/6/27 09:15
 * @Description:
 */
@Configuration
@ConditionalOnClass(CamelRouterService.class)
@ComponentScan("com.zhengtoon.framework.camel.components")
public class ZhengToonCamelConfiguration{
    private final Logger logger = LoggerFactory.getLogger(ZhengToonCamelConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(CamelRouterService.class)
    public CamelRouterService camelRouterService() {
        logger.info("init CamelRouterService start ");
        CamelRouterService camelRouterService = new CamelRouterService();
        camelRouterService.init();
        logger.info("init CamelRouterService end ");
        return camelRouterService;
    }
}
