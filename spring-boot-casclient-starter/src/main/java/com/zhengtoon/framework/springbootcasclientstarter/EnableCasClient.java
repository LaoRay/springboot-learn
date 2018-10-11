package com.zhengtoon.framework.springbootcasclientstarter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description 基于注解方式  配置该注解开启cas功能
 * @param  
 * @return  
 * @author 601478
 * @date 2018/9/30 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(CasClientConfiguration.class)
public @interface EnableCasClient {
}
