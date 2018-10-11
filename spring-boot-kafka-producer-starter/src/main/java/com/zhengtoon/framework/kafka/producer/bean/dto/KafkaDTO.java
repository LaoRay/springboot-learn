package com.zhengtoon.framework.kafka.producer.bean.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * KafkaDTO
 *
 * @author qindaorong
 * @date 2017/10/16
 */
@Data
@ToString
public class KafkaDTO implements Serializable {

    private static final long serialVersionUID = 4761131244276697160L;

    private String topicId;

    private String timeStamp;

    private String content;
}
