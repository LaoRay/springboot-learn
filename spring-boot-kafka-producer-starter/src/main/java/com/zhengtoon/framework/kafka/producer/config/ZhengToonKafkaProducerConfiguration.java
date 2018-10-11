package com.zhengtoon.framework.kafka.producer.config;

import com.zhengtoon.framework.kafka.producer.service.KakaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: qindaorong
 * @Date: 2018/6/27 09:15
 * @Description:
 */
@Configuration
@ComponentScan({"com.zhengtoon.framework.kafka.producer.config","com.zhengtoon.framework.kafka.producer.common"})
public class ZhengToonKafkaProducerConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ZhengToonKafkaProducerConfiguration.class);

    @Bean
    public KakaProducerService kakaProducerService(){
        return new KakaProducerService();
    }
}
