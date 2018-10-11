package zhengtoon.framework.task.manager.starter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import zhengtoon.framework.task.manager.starter.service.impl.TaskMemoryServiceImpl;

/**
 * @Description:
 * @author:sanchuan.li
 * @date:2018/8/21
 * @version:1.0
 * @Copyright:2018 www.zhengtoon.com Inc. All rights reserved.
 * @Company:Beijing Siyuan Zhengtoon Technology Group Co., Ltd.
 */
@Configuration
@ComponentScan({"zhengtoon.framework.task.manager.starter"})
@Slf4j
public class TaskManagerStarterConfiguration {

    @Bean
    @ConditionalOnMissingBean(TaskMemoryServiceImpl.class)
    public TaskMemoryServiceImpl taskManagerService() {
        TaskMemoryServiceImpl taskManagerService = new TaskMemoryServiceImpl();
        return taskManagerService;
    }


}
