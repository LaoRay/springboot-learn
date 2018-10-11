package com.zhengtoon.framework.lock.aop;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.zhengtoon.framework.lock.annotions.RedisLock;
import com.zhengtoon.framework.lock.components.RedisDistributedLock;
import com.zhengtoon.framework.lock.interfaces.DistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


/**
 * @author fuwei.deng
 * @date 2017年6月14日 下午3:11:22
 * @version 1.0.0
 */
@Aspect
@Configuration
@ConditionalOnClass(DistributedLock.class)
public class DistributedLockAspectConfiguration {

    private final Logger logger = LoggerFactory.getLogger(DistributedLockAspectConfiguration.class);

    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @Pointcut("@annotation(com.zhengtoon.framework.lock.annotions.RedisLock)")
    private void lockPoint(){

    }

    @Around("lockPoint()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        String key = redisLock.value();
        if(StringUtils.isEmpty(key)){
            Object[] args = pjp.getArgs();
            key = Arrays.toString(args);
        }

        boolean islock = redisDistributedLock.lock(key,Long.valueOf(redisLock.keepMills()).intValue(),Long.valueOf(redisLock.timeoutMills()).intValue());

        if(!islock){
            return null;
        }

        //得到锁,执行方法，释放锁
        logger.debug("get lock success : " + key);

        try {
            return pjp.proceed();
        } catch (Exception e) {
            logger.error("execute locked method occured an exception", e);
        } finally {
            redisDistributedLock.unlock(key);
            logger.debug("release lock !");
        }
        return null;
    }
}
