package zhengtoon.framework.task.manager.starter.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import zhengtoon.framework.task.manager.starter.domain.XmlTaskList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
/**
 * @Description:
 * @author:sanchuan.li
 * @date:2018/8/21
 * @version:1.0
 * @Copyright:2018 www.zhengtoon.com Inc. All rights reserved.
 * @Company:Beijing Siyuan Zhengtoon Technology Group Co., Ltd.
 */
@Slf4j
public class XmlBuilder {


    final static String TASK_DEFAULT_URL = "/task_default.xml";

    final static String TASK_URL = "/task.xml";

     public static XmlTaskList loadTasks () throws Exception {
        JAXBContext jaxbContext;
        XmlTaskList xmlTaskList;

        jaxbContext = JAXBContext.newInstance(XmlTaskList.class);
        Unmarshaller um = jaxbContext.createUnmarshaller();
        ClassPathResource cpr = new ClassPathResource(TASK_URL);
        if (!cpr.exists()) {
            log.error("The task.xml is not exists!");
            cpr = new ClassPathResource(TASK_DEFAULT_URL);
        }
        xmlTaskList = (XmlTaskList) um.unmarshal(cpr.getInputStream());
        return xmlTaskList;
    }

}
