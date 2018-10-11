package com.zhengtoon.framework;


import com.zhengtoon.framework.uias.UiasConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UiasConfig.class)
public class MainTests {

	@Autowired
	TestRestTemplate testRestTemplate;

}
