package com.zhengtoon.framework.lock;

import com.zhengtoon.framework.lock.components.RedisDistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @auther: qindaorong
 * @Date: 2018/6/27 09:15
 * @Description:
 */
@Configuration
@ConditionalOnClass({RedisTemplate.class})
@ComponentScan
public class ZhengToonRedisLockConfiguration {
    private final Logger logger = LoggerFactory.getLogger(ZhengToonRedisLockConfiguration.class);

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public RedisDistributedLock redisDistributedLock(RedisTemplate<Object, Object> redisTemplate) {
        return new RedisDistributedLock(redisTemplate);
    }
}
