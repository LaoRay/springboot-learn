package com.zhengtoon.framework.cloud.configuration;

import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.zhengtoon.framework.cloud.bean.enums.QiniuLocalEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 七牛接口配置类
 *
 * @author LuCheng.Qi
 * @since 2018-09-26
 * Company:北京思源政务通有限公司
 */
@Configuration
@ConditionalOnProperty(name = "scloud.type", havingValue = "qiniu")
@EnableConfigurationProperties(StorageConfig.class)
public class QiniuConfigure {

	@Autowired
	private StorageConfig storageConfig;

	@Bean
	public com.qiniu.storage.Configuration createConfiguration() {
		return new com.qiniu.storage.Configuration(queryQiniuZone());
	}

	@Bean
	public UploadManager createUploadManager(com.qiniu.storage.Configuration cfg) {
		return new UploadManager(cfg);
	}


	@Bean
	public Auth createAuth() {
		return Auth.create(storageConfig.getQiniu().getAccessKey(), storageConfig.getQiniu().getSecretKey());
	}


	private Zone queryQiniuZone() {
		QiniuLocalEnum local = storageConfig.getQiniu().getLocal();
		switch (local) {
			case hb:
				return Zone.zone1();
			case hn:
				return Zone.zone2();
			case hd:
				return Zone.zone0();
			default:
				return null;
		}
	}


}
