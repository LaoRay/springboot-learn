package com.zhengtoon.framework;


import com.zhengtoon.framework.web.WebConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebConfig.class)
//@WebAppConfiguration
//@ContextConfiguration
@EnableWebMvc
public class MainTests {

	@Autowired
	private OkHttpClient okHttpClient;

	@Test
	public void test() {
		Request.Builder builder = new Request.Builder()
				.url("http://192.168.1.123:8888").get();
		try {
			Response execute = okHttpClient.newCall(builder.build()).execute();
			System.out.println(execute.body().string());
		} catch (IOException e) {
			System.out.println("出错了");
			e.printStackTrace();
		}
	}

}
