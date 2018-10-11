##### spring-boot-kafka-producer-starter 功能介绍
zhengtoon 根据自己的业务特点封装的用于kafka消息发送的starter


##### spring-boot-kafka-producer-starter 使用介绍
1.在使用starter的工程下引入spring-boot-email-starter 的依赖
```
        <dependency>
            <groupId>com.zhengtoon.framework</groupId>
            <artifactId>spring-boot-kafka-producer-starter</artifactId>
            <version>1.0.0</version>
        </dependency>
```

2.使用样例，在对应使用的Service或者Controller层引用
```
    //使用时注入
    @Autowired
    private KakaProducerService kakaProducerService;
    
    
    @RequestMapping(value = { "/kafka" }, method = RequestMethod.GET)
    public Object kafka(){
        KafkaDTO kafkaDTO = new KafkaDTO();
        kafkaDTO.setTopicId("zhengtoon_topic");
        kafkaDTO.setContent("qindaorong_test_message");
        kakaProducerService.sendMessage(kafkaDTO);
        return "ok";
    }
```


##### 配置参数
在resources目录下新建kafka.properties资源文件
```
bootstrap.servers=172.28.18.46:9092,172.28.18.47:9092,172.28.18.45:9092
acks=all
retries=2
batch.size=16384
linger.ms=1
buffer.memory=33554432
key.serializer=org.apache.kafka.common.serialization.StringSerializer
value.serializer=org.apache.kafka.common.serialization.StringSerializer
```