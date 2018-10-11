package com.zhengtoon.framework.cloud.configuration;

import com.systoon.scloud.master.lightsdk.server.BaseDeleteService;
import com.systoon.scloud.master.lightsdk.server.BaseDownloadService;
import com.systoon.scloud.master.lightsdk.server.BaseUpdateService;
import com.systoon.scloud.master.lightsdk.server.BaseUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 思源云接口配置类，统一配置方便管理
 *
 * @author LuCheng.Qi
 * @since 2018-07-10
 * Company:北京思源政务通有限公司
 */
@Configuration
@ConditionalOnProperty(name = "scloud.type", havingValue = "toon")
@EnableConfigurationProperties(StorageConfig.class)
public class SCloudServiceConfigure {

	@Autowired
	private StorageConfig storageConfig;

	@Bean
	public BaseUploadService createBaseUploadService() {
		StorageConfig.ToonCloud toonCloud = storageConfig.getToon();
		return new BaseUploadService(
				toonCloud.getScloudCloudAppId(),
				toonCloud.getScloudServerHost(),
				toonCloud.getScloudAccessKeySecret(),
				toonCloud.getScloudAssessKeyId()
		);
	}


	@Bean
	public BaseDeleteService createBaseDeleteService() {
		StorageConfig.ToonCloud toonCloud = storageConfig.getToon();
		return new BaseDeleteService(
				toonCloud.getScloudCloudAppId(),
				toonCloud.getScloudServerHost(),
				toonCloud.getScloudAccessKeySecret(),
				toonCloud.getScloudAssessKeyId()
		);
	}


	@Bean
	public BaseDownloadService createBaseDownloadService() {
		StorageConfig.ToonCloud toonCloud = storageConfig.getToon();
		return new BaseDownloadService(
				toonCloud.getScloudCloudAppId(),
				toonCloud.getScloudServerHost(),
				toonCloud.getScloudAccessKeySecret(),
				toonCloud.getScloudAssessKeyId()
		);
	}

	@Bean
	public BaseUpdateService createBaseUpdateService() {
		StorageConfig.ToonCloud toonCloud = storageConfig.getToon();
		return new BaseUpdateService(
				toonCloud.getScloudCloudAppId(),
				toonCloud.getScloudServerHost(),
				toonCloud.getScloudAccessKeySecret(),
				toonCloud.getScloudAssessKeyId()
		);
	}

}
