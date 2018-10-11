package com.zhengtoon.framework;


import com.zhengtoon.framework.disconf.DisconfAutoConfigure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DisconfAutoConfigure.class)
public class MainTests {

//	@Autowired
//	private DisconfProperties disconfProperties;

	@Value("${spring.redis.host}")
	private String redis;

	@Test
	public void contextLoads() {

		System.out.println("读取到的值："+redis);
	}

}
