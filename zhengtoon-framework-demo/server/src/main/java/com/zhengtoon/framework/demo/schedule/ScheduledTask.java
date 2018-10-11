package com.zhengtoon.framework.demo.schedule;

import com.zhengtoon.framework.lock.annotions.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 定时任务, 采用分布式锁的来保证任务不会产生并发
 */
@Slf4j
@Component
@EnableScheduling
public class ScheduledTask{

	/**
	 * 定时器的表达式采用配置的方式
	 * redislock的名字需要填写，不同的定时任务redislock的value需要不同
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "0 8 17 * * ?")
	@RedisLock(value = "lock-1")
	public void scheduled1() throws InterruptedException {
		log.debug("定时任务开始执行1");
		TimeUnit.MILLISECONDS.sleep(10000);
	}


	/**
	 * 定时器的表达式采用配置的方式
	 * redislock的名字需要填写，不同的定时任务redislock的value需要不同
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "0 8 17 * * ?")
	@RedisLock(value = "lock-1")
	public void scheduled2() throws InterruptedException {
		log.debug("定时任务开始执行2");
		TimeUnit.MILLISECONDS.sleep(10000);
	}
}
