package zhengtoon.framework.task.starter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import zhengtoon.framework.task.starter.components.HealthWatcher;
import zhengtoon.framework.task.starter.dto.HttpConnDto;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 17:15
 * @Description:
 */
@Configuration
@ComponentScan({"zhengtoon.framework.task.starter.service"})
@Slf4j
public class ZhengToonTaskStarterConfiguration {
    @Value("${zhengtoon.framework.taskManageAddress:}")
    private String taskManageAddress;

    @Value("${zhengtoon.framework.taskManageUserName:}")
    private String taskManageUserName;

    @Value("${zhengtoon.framework.taskManagePassword:}")
    private String taskManagePassword;

    @Value("${zhengtoon.healthWatcher.switch:}")
    private Boolean healthWatcherSwitch = true;

    @Bean
    public HttpConnDto httpConnDto(){
        if(StringUtils.isEmpty(taskManageAddress) ){
            log.warn("can not connect task manage, check value of [zhengtoon.framework.taskManageAddress].");
        }else if(StringUtils.isEmpty(taskManageUserName) ){
            log.warn("can not connect task manage, check value of [zhengtoon.framework.taskManageUserName].");
        }else if(StringUtils.isEmpty(taskManagePassword) ){
            log.warn("can not connect task manage, check value of [zhengtoon.framework.taskManagePassword].");
        }else{
            return new HttpConnDto(taskManageAddress,taskManageUserName,taskManagePassword);
        }
        return null;
    }

    @Bean
    public HealthWatcher healthWatcher(){
        HttpConnDto httpConnDto = this.httpConnDto();
        if(healthWatcherSwitch){
            return new HealthWatcher(httpConnDto);
        }else{
            return null;
        }
    }
}
