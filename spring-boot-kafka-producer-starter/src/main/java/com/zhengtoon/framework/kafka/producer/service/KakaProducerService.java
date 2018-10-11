package com.zhengtoon.framework.kafka.producer.service;

import com.zhengtoon.framework.kafka.producer.bean.dto.KafkaDTO;
import com.zhengtoon.framework.kafka.producer.common.ProducerPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther: qindaorong
 * @Date: 2018/8/3 09:35
 * @Description:
 */
@Service
public class KakaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KakaProducerService.class);


    @Autowired
    ProducerPool producerPool;

    /**
     * 向kafka压入一条数据
     * @param kafkaDTO
     */
    public void sendMessage(KafkaDTO kafkaDTO){
        logger.debug("[KakaProducerService] [sendMessage] start!");
        producerPool.startSendMessage(kafkaDTO);
        logger.debug("[KakaProducerService] [sendMessage] over!");
    }
}
