package com.zhengtoon.framework.cloud.service.impl;


import com.alibaba.fastjson.JSON;
import com.systoon.scloud.master.lightsdk.request.*;
import com.systoon.scloud.master.lightsdk.server.BaseDeleteService;
import com.systoon.scloud.master.lightsdk.server.BaseDownloadService;
import com.systoon.scloud.master.lightsdk.server.BaseUploadService;
import com.zhengtoon.framework.cloud.bean.dto.BaseFileDTO;
import com.zhengtoon.framework.cloud.bean.dto.FileResult;
import com.zhengtoon.framework.cloud.bean.dto.UploadDTO;
import com.zhengtoon.framework.cloud.bean.dto.param.ToonParam;
import com.zhengtoon.framework.cloud.configuration.StorageConfig;
import com.zhengtoon.framework.cloud.service.CloudStrorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Slf4j
public class ToonCloudStrorageImpl implements CloudStrorage {

	@Autowired
	private StorageConfig storageConfig;

	@Autowired
	private BaseUploadService baseUploadService;

	@Autowired
	private BaseDeleteService baseDeleteService;

	@Autowired
	private BaseDownloadService baseDownloadService;

	@Override
	public FileResult upload(UploadDTO uploadFileDTO) {
		ToonParam toonParam = (ToonParam) uploadFileDTO.getParam();
		BaseRequestUpload upload = new BaseRequestUpload();
		upload = (BaseRequestUpload) this.setBaseRequest(upload, storageConfig.getToon());
		upload.setPub(toonParam.getIsPublic().getValue());
		upload.setClientIp(toonParam.getClientIp());
		upload.setLocation(toonParam.getLocation());
		upload.setSuffix(uploadFileDTO.getSuffix());
		log.debug("文件上传参数[{}]", JSON.toJSONString(upload));
		try {
			RequestUploadResult result = baseUploadService.directUploadFile
					(upload, uploadFileDTO.getFileBytes());
			log.debug("文件上传返回值[{}]", result);
			return new FileResult(result.getStoid(), result.getPubUrl());
		} catch (Exception e) {
			log.error("文件上传失败", e);
		}
		return null;
	}

	@Override
	public byte[] download(BaseFileDTO baseFileDTO) {
		ToonParam toonParam = (ToonParam) baseFileDTO.getParam();
		BaseRequestDownload baseRequestDownload = new BaseRequestDownload();
		baseRequestDownload.setClientIp(toonParam.getClientIp());
		baseRequestDownload.setLocation(toonParam.getLocation());
		baseRequestDownload.setStoid(baseFileDTO.getId());
		log.debug("文件下载参数[{}]", JSON.toJSONString(baseFileDTO));
		try {
			return baseDownloadService.directDownload(baseRequestDownload);
		} catch (Exception e) {
			log.error("文件下载失败", e);
		}
		return null;
	}


	@Override
	public boolean delete(BaseFileDTO baseFileDTO) {
		ToonParam toonParam = (ToonParam) baseFileDTO.getParam();
		RequestDelete requestDelete = new RequestDelete();
		requestDelete = (RequestDelete) this.setBaseRequest(requestDelete, storageConfig.getToon());
		requestDelete.setClientIp(toonParam.getClientIp());
		requestDelete.setLocation(toonParam.getLocation());
		requestDelete.setStoid(baseFileDTO.getId());
		log.debug("文件删除参数[{}]", JSON.toJSONString(requestDelete));
		try {
			baseDeleteService.deleteFile(requestDelete);
			return true;
		} catch (Exception e) {
			log.error("文件删除失败", e);
		}
		return false;
	}

	/**
	 * 基本参数设置
	 *
	 * @param baseRequest 参数对象
	 * @param toonCloud   配置信息
	 * @return 参数对象
	 */
	private AccessRequest setBaseRequest(AccessRequest baseRequest, StorageConfig.ToonCloud toonCloud) {
		baseRequest.setSignature(toonCloud.getScloudSignature());
		baseRequest.setAccessKeyId(toonCloud.getScloudAssessKeyId());
		baseRequest.setAppId(toonCloud.getScloudCloudAppId());
		baseRequest.setTrace_reserve_mark(UUID.randomUUID().toString());
		return baseRequest;
	}

}
