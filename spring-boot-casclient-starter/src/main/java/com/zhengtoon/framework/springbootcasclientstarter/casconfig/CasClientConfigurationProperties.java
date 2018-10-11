package com.zhengtoon.framework.springbootcasclientstarter.casconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * @program: cas-client-spring-boot-starter
 * @description: cas springboot 配置类
 * @author: 601478
 * @create: 2018-09-30 09:42
 **/
@Data
@ConfigurationProperties(prefix = "cas", ignoreUnknownFields = false)
public class CasClientConfigurationProperties {

    /**
     * CAS 服务端 url 不能为空
     */
    @NotNull
    private String serverUrlPrefix;

    /**
     * CAS 服务端登录地址  上面的连接 加上/login 该参数不能为空
     */
    @NotNull
    private String serverLoginUrl;

    /**
     * 当前客户端的地址
     */
    @NotNull
    private String clientHostUrl;

    /**
     * 忽略规则,访问那些地址 不需要登录
     */
    private String ignorePattern;

    /**
     * 自定义UrlPatternMatcherStrategy验证
     */
    private String ignoreUrlPatternType;

}
