package com.zhengtoon.framework;


import com.zhengtoon.framework.message.MessageConfig;
import com.zhengtoon.framework.message.im.bean.IMResult;
import com.zhengtoon.framework.message.im.bean.NoticeSimpleParam;
import com.zhengtoon.framework.message.im.service.ToonIMService;
import com.zhengtoon.framework.message.im.common.IMEntityFactory;
import com.zhengtoon.framework.web.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.util.UUID;

@RunWith(SpringRunner.class)
@EnableWebMvc
@SpringBootTest(classes = {MessageConfig.class, WebConfig.class})
public class MainTests {

	@Autowired
	private ToonIMService toonIMService;

	@Test
	public void test() throws IOException {
		IMResult imResult = toonIMService.sendMessage(
				IMEntityFactory.createNoticeEntity(NoticeSimpleParam.builder()
						.msgID(UUID.fromString("f2420331-1f5b-4cad-9103-1ac9663f38c5"))
						.bizNo(UUID.fromString("f2420331-1f5b-4cad-9103-1ac9663f38c1"))
						.content("这是内容")
						.title("这是标题")
						.pushInfo("推送消息")
						.toClient("411382")
						.btnContent("这是按钮")
						.btnUrl("http://www.qqzhi.com/uploadpic/2015-01-18/002442661.jpg")
						.imgUrl("http://img0.bdstatic.com/static/searchresult/img/logo-2X_b99594a.png")
						.imgRedictUrl("http://img3.duitang.com/uploads/item/201409/25/20140925100559_RviGZ.jpeg").build()));
		System.out.println(imResult);
	}

}
