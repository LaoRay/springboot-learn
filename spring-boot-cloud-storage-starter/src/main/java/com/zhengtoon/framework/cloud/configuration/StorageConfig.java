package com.zhengtoon.framework.cloud.configuration;

import com.zhengtoon.framework.cloud.bean.enums.CloudTypeEnum;
import com.zhengtoon.framework.cloud.bean.enums.QiniuLocalEnum;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 云存储配置
 */
@Data
@ConfigurationProperties(prefix = "scloud")
public class StorageConfig {
	/**
	 * 云类型 思源云，fastdfs, 七牛
	 */
	private CloudTypeEnum type;
	/**
	 * 思源云存储配置
	 */
	private ToonCloud toon = new ToonCloud();

	/**
	 * 七牛配置
	 */
	private Qiniu qiniu = new Qiniu();

	@Data
	public static class ToonCloud {
		private String scloudServerHost;
		private Integer scloudCloudAppId;
		private String scloudAssessKeyId;
		private String scloudAccessKeySecret;
		private String scloudSignature;
	}

	@Data
	public static class Qiniu {
		/**
		 * 服务器位置
		 */
		private QiniuLocalEnum local;
		/**
		 * accessKey
		 */
		private String accessKey;
		/**
		 * secretKey
		 */
		private String secretKey;
		/**
		 * 存储空间名字
		 */
		private String bucket;
		/**
		 * 存储平台域名，用于文件下载
		 */
		private String domainName;
	}

}
