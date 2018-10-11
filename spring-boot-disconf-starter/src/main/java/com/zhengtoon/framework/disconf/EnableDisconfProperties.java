package com.zhengtoon.framework.disconf;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用disconf配置项
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({DisconfAutoConfigure.class})
public @interface EnableDisconfProperties {
}
