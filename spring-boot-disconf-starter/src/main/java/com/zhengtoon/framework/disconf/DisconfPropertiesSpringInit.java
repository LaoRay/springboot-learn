package com.zhengtoon.framework.disconf;

import com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.IOException;

/**
 * 将disconf的配置文件加载到spring环境中
 *
 * @author LuCheng.Qi
 * @since 2018-07-12
 * Company:北京思源政务通有限公司
 */
@Slf4j
public class DisconfPropertiesSpringInit implements BeanFactoryPostProcessor {

	private static final String DISCONF_PROPERTY_SOURCE_NAME = "DisConfPropertySources";

	private ReloadablePropertiesFactoryBean reloadablePropertiesFactoryBean;

	private ConfigurableEnvironment environment;

	DisconfPropertiesSpringInit(ReloadablePropertiesFactoryBean reloadablePropertiesFactoryBean, ConfigurableEnvironment environment) {
		this.reloadablePropertiesFactoryBean = reloadablePropertiesFactoryBean;
		this.environment = environment;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (environment.getPropertySources().contains(DISCONF_PROPERTY_SOURCE_NAME)) {
			//already initialized
			return;
		}
		CompositePropertySource composite = new CompositePropertySource(DISCONF_PROPERTY_SOURCE_NAME);
		try {
			composite.addPropertySource(new PropertiesPropertySource(DISCONF_PROPERTY_SOURCE_NAME, reloadablePropertiesFactoryBean.getObject()));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		environment.getPropertySources().addFirst(composite);
	}
}
