package com.zhengtoon.framework.cloud;

import com.zhengtoon.framework.cloud.bean.enums.CloudTypeEnum;
import com.zhengtoon.framework.cloud.configuration.StorageConfig;
import com.zhengtoon.framework.cloud.service.CloudStrorage;
import com.zhengtoon.framework.cloud.service.impl.FastDFSStrorageImpl;
import com.zhengtoon.framework.cloud.service.impl.QiniuStrorageImpl;
import com.zhengtoon.framework.cloud.service.impl.ToonCloudStrorageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 云储存配置类
 *
 * @author LuCheng.Qi
 * @since 2018-07-09
 * Company:北京思源政务通有限公司
 */
@Configuration
@ComponentScan
@ConditionalOnProperty(name = "scloud.type")
@EnableConfigurationProperties(StorageConfig.class)
public class CloudStorageConfigure {

	@Autowired
	private StorageConfig storageConfig;

	/**
	 * 创建云存储访问对象
	 *
	 * @return CloudStrorage
	 */
	@Bean
	public CloudStrorage createCloudStorage() {
		CloudTypeEnum cloudTypeEnum = storageConfig.getType();
		switch (cloudTypeEnum) {
			case toon:
				return new ToonCloudStrorageImpl();
			case fastdfs:
				return new FastDFSStrorageImpl();
			case qiniu:
				return new QiniuStrorageImpl();
			default:
				return null;
		}
	}
}
