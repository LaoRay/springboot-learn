package com.zhengtoon.framework;


import com.zhengtoon.framework.jwt.JwtConfig;
import com.zhengtoon.framework.jwt.bean.JwtResult;
import com.zhengtoon.framework.jwt.security.TokenHelper;
import com.zhengtoon.framework.utils.codec.DesUtils;
import com.zhengtoon.framework.web.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JwtConfig.class, WebConfig.class})
@EnableWebMvc
public class MainTests {

	@Autowired
	private TokenHelper tokenHelper;

	@Test
	public void test(){
		JwtResult jwtResult = tokenHelper.generateToken("qqq");
		System.out.println(jwtResult);
	}


	@Test
	public void test1(){
		System.out.println(DesUtils.decode("E61B7C1EA6B33152", "qqqqqqqq"));
	}
}
