package com.zhengtoon.framework.kafka.producer.common;

import com.zhengtoon.framework.kafka.producer.bean.dto.KafkaDTO;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * @auther: qindaorong
 * @Date: 2018/8/3 09:56
 * @Description:
 */
@Component
public class ProducerPool {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);
    private static final int POOL_SIZE = 50;

    private ExecutorService executorService;

    @Autowired
    KafkaProducer<String, String> producer;

    @PostConstruct
    public void init(){
        executorService = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    public void startSendMessage(KafkaDTO kafkaDTO){
        Future<Boolean> future = executorService.submit(new Runner(kafkaDTO,producer));
        try {
            logger.info("[sendMessage] has bean finished! return value is {}",future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    class Runner implements Callable<Boolean>{

        private final Logger logger = LoggerFactory.getLogger(Runner.class);

        KafkaProducer<String, String> producer;

        private KafkaDTO kafkaDTO;

        public Runner(KafkaDTO kafkaDTO, KafkaProducer<String, String>  producer){
            this.kafkaDTO = kafkaDTO;
            this.producer = producer;
        }

        @Override
        public Boolean call(){

            Boolean isSend = Boolean.FALSE;
            if (!StringUtils.isEmpty(kafkaDTO.getTopicId())) {
                kafkaDTO.setTimeStamp(String.valueOf(System.currentTimeMillis()));

                String timeStamp = "[Producer send time is " + String.valueOf(System.currentTimeMillis()) + "]";
                try {
                    //如果没有key默认随机随机分区
                    RecordMetadata recordMetadata = producer.send(new ProducerRecord<String, String>(kafkaDTO.getTopicId(), timeStamp, kafkaDTO.toString())).get();
                    String partition = String.valueOf(recordMetadata.partition());
                    String topicName = recordMetadata.topic();
                    logger.info("kafka message already send , partition is [{}] , topic name is [{}] ,message is [{}] ", partition, topicName,kafkaDTO.toString());

                    isSend = Boolean.TRUE;

                } catch (InterruptedException e) {
                    logger.error("kafka message has [InterruptedException], message is {}",e.getMessage());
                } catch (ExecutionException e) {
                    logger.error("kafka message has [ExecutionException], message is {}",e.getMessage());
                } catch (Exception e){
                    logger.error("kafka message has [Exception], message is {}",e.getMessage());
                }
            }
            return isSend;
        }
    }
}
