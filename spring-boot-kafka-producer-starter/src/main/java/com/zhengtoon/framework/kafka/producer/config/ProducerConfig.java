package com.zhengtoon.framework.kafka.producer.config;

import com.zhengtoon.framework.kafka.producer.common.KafkaConstant;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Properties;


/**
 * ProducerConfig
 *
 * @author qindaorong
 * @date 2017/10/16PropertySource
 */
@Configuration
@PropertySource("classpath:/kafka.properties")
public class ProducerConfig {

    @Autowired
    private Environment env;

    @Bean
    public KafkaProducer<String, String> kafkaProducer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", env.getProperty(KafkaConstant.BOOTSTRAP_SERVERS));
        props.put("acks", env.getProperty(KafkaConstant.ACKS));
        props.put("retries", env.getProperty(KafkaConstant.RETRIES));
        props.put("batch.size", env.getProperty(KafkaConstant.BATCH_SIZE));
        props.put("linger.ms", env.getProperty(KafkaConstant.LINGER_MS));
        props.put("buffer.memory", env.getProperty(KafkaConstant.BUFFER_MEMORY));
        props.put("key.serializer", env.getProperty(KafkaConstant.KEY_SERIALIZER));
        props.put("value.serializer", env.getProperty(KafkaConstant.VALUE_SERIALIZER));
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        return producer;
    }
}
