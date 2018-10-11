package com.zhengtoon.framework.cloud.service.impl;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.zhengtoon.framework.cloud.bean.dto.BaseFileDTO;
import com.zhengtoon.framework.cloud.bean.dto.FileResult;
import com.zhengtoon.framework.cloud.bean.dto.UploadDTO;
import com.zhengtoon.framework.cloud.configuration.StorageConfig;
import com.zhengtoon.framework.cloud.service.CloudStrorage;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 七牛云实现
 */
@Slf4j
public class QiniuStrorageImpl implements CloudStrorage {

	@Autowired
	private UploadManager uploadManager;

	@Autowired
	private Configuration cfg;

	@Autowired
	private Auth auth;

	@Autowired
	private StorageConfig storageConfig;

	private OkHttpClient okHttpClient;

	@Override
	public FileResult upload(UploadDTO uploadFileDTO) {
		String token = auth.uploadToken(storageConfig.getQiniu().getBucket());
		try {
			Response response = uploadManager.put(uploadFileDTO.getFileBytes(),
					UUID.randomUUID().toString(), token);
			log.debug("文件上传返回值[ {} ]", response);
			//解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			log.debug("文件id:[{}]", putRet.key);
			FileResult fileResult = new FileResult();
			fileResult.setId(putRet.key);
			fileResult.setUrl(createStaticResource(putRet.key));
			return fileResult;
		} catch (QiniuException e) {
			log.error("文件上传失败", e);
		}
		return null;
	}

	@Override
	public byte[] download(BaseFileDTO downloadDTO) {
		String url = this.createStaticResource(downloadDTO.getId());
		try {
			okhttp3.Response response = okHttpClient.newCall(new Request.Builder()
					.url(url).get().build()).execute();
			return response.body().bytes();
		} catch (IOException e) {
			log.error("文件下载异常", e);
		}
		return null;
	}

	@Override
	public boolean delete(BaseFileDTO baseFileDTO) {
		BucketManager bucketManager = new BucketManager(auth, cfg);
		try {
			bucketManager.delete(storageConfig.getQiniu().getBucket(), baseFileDTO.getId());
			return true;
		} catch (QiniuException e) {
			//如果遇到异常，说明删除失败
			log.error("文件删除失败", e);
		}
		return false;
	}


	/**
	 * 创建静态资源路径
	 *
	 * @return 静态路径
	 */
	private String createStaticResource(String key) {
		return String.format("%s/%s", storageConfig.getQiniu().getDomainName(), key);
	}


	@PostConstruct
	public void initOkHttpClient() {
		this.okHttpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
				.connectTimeout(60, TimeUnit.SECONDS)
				.writeTimeout(60, TimeUnit.SECONDS)
				.connectionPool(new ConnectionPool(
						60, 60, TimeUnit.SECONDS))
				.addInterceptor(new Interceptor() {
					@Override
					public okhttp3.Response intercept(final Chain chain) throws IOException {
						final Request request = chain.request();
						okhttp3.Response response = chain.proceed(request);
						log.debug("Request method = {} url = {} Response = {}", request.method(), request.url(), response);
						return response;
					}
				}).build();
	}
}