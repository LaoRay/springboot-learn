package com.zhengtoon.framework.cloud.service.impl;


import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.zhengtoon.framework.cloud.bean.dto.BaseFileDTO;
import com.zhengtoon.framework.cloud.bean.dto.FileResult;
import com.zhengtoon.framework.cloud.bean.dto.UploadDTO;
import com.zhengtoon.framework.cloud.bean.dto.param.FdfsParam;
import com.zhengtoon.framework.cloud.service.CloudStrorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;

@Slf4j
public class FastDFSStrorageImpl implements CloudStrorage {

	@Autowired
	private FastFileStorageClient storageClient;

	@Override
	public FileResult upload(UploadDTO uploadFileDTO) {
		try {
			FdfsParam fdfsParam = (FdfsParam) uploadFileDTO.getParam();
			StorePath storePath = storageClient.uploadFile(
					new ByteArrayInputStream(uploadFileDTO.getFileBytes()),
					fdfsParam.getFileSize(),
					uploadFileDTO.getSuffix(),
					null);
			log.debug("文件上传返回值[{}]", storePath);
			FileResult fileResult = new FileResult();
			fileResult.setId(storePath.getFullPath());
			return fileResult;
		} catch (Exception e) {
			log.error("文件上传失败", e);
		}
		return null;
	}

	@Override
	public byte[] download(BaseFileDTO baseFileDTO) {
		try {
			DownloadByteArray callback = new DownloadByteArray();
			StorePath storePath = StorePath.praseFromUrl(baseFileDTO.getId());
			return storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), callback);
		} catch (Exception e) {
			log.error("文件下载失败", e);
		}
		return null;
	}

	@Override
	public boolean delete(BaseFileDTO baseFileDTO) {
		try {
			StorePath storePath = StorePath.praseFromUrl(baseFileDTO.getId());
			storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
			return true;
		} catch (Exception e) {
			log.error("文件删除失败", e);
		}
		return false;
	}
}
