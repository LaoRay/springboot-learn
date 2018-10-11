package com.zhengtoon.framework.disconf;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * disconf配置类
 *
 * @author LuCheng.Qi
 * @since 2018-07-12
 * Company:北京思源政务通有限公司
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "disconf.properties.enable", havingValue = "true", matchIfMissing = true)
public class DisconfAutoConfigure {

	/**
	 * 准备disconf环境
	 */
	@Bean(destroyMethod = "destroy")
	public DisconfMgrBean disconfMgrBean(Environment environment) {
		String scanPackage = environment.getProperty("disconf.scan.package");
		if (StringUtils.isBlank(scanPackage)) {
			scanPackage = "com.zhengtoon";
		}
		DisconfMgrBean bean = new DisconfMgrBean();
		bean.setScanPackage(scanPackage);
		return bean;
	}

	/**
	 * 二次扫描
	 */
	@Bean(initMethod = "init", destroyMethod = "destroy")
	public DisconfMgrBeanSecond disconfMgrBeanSecond() {
		return new DisconfMgrBeanSecond();
	}

	/**
	 * disconf 托管配置 ，配置更改会自动reload
	 */
	@Bean
	public ReloadablePropertiesFactoryBean reloadablePropertiesFactoryBean(Environment environment) {
		ReloadablePropertiesFactoryBean bean = new ReloadablePropertiesFactoryBean();
		String property = environment.getProperty("disconf.properties.names");
		List<String> propertiesList = new ArrayList<>();
		if (StringUtils.isNotBlank(property)) {
			propertiesList.addAll(Arrays.asList(property.split(",")));
		}
		bean.setLocations(propertiesList);
		return bean;
	}

	/**
	 * 将disconf的配置文件信息，加载到spring环境中
	 */
	@Bean
	@Order(value = Ordered.HIGHEST_PRECEDENCE)
	public DisconfPropertiesSpringInit disconfPropertiesSpringInit(
			ReloadablePropertiesFactoryBean reloadablePropertiesFactoryBean,
			ConfigurableEnvironment environment) {
		return new DisconfPropertiesSpringInit(reloadablePropertiesFactoryBean, environment);
	}

}
