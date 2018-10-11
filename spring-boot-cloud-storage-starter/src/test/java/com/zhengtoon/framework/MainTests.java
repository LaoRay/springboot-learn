package com.zhengtoon.framework;


import com.zhengtoon.framework.cloud.CloudStorageConfigure;
import com.zhengtoon.framework.cloud.bean.dto.BaseFileDTO;
import com.zhengtoon.framework.cloud.bean.dto.FileResult;
import com.zhengtoon.framework.cloud.bean.dto.UploadDTO;
import com.zhengtoon.framework.cloud.bean.dto.param.FdfsParam;
import com.zhengtoon.framework.cloud.bean.dto.param.QiniuParam;
import com.zhengtoon.framework.cloud.bean.dto.param.ToonParam;
import com.zhengtoon.framework.cloud.service.CloudStrorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudStorageConfigure.class)
@EnableConfigurationProperties
public class MainTests {

	@Autowired
	private CloudStrorage cloudStrorage;

	@Test
	public void testToonUpload() throws IOException {
		byte[] bytes = IOUtils.toByteArray(new FileSystemResource("C:\\test\\1.jpg").getInputStream());
		UploadDTO<ToonParam> uploadDTO = new UploadDTO<>();
		uploadDTO.setFileBytes(bytes);
		uploadDTO.setSuffix(".jpg");
		ToonParam param = new ToonParam();
		param.setClientIp("192.168.1.1");
		uploadDTO.setParam(param);
		FileResult upload = cloudStrorage.upload(uploadDTO);
		System.out.println("id："+ upload.getId());
		System.out.println("url："+ upload.getUrl());
		BaseFileDTO<ToonParam> baseFileDTO = new BaseFileDTO<>();
		baseFileDTO.setParam(param);
		baseFileDTO.setId( upload.getId());
		byte[] download = cloudStrorage.download(baseFileDTO);
//		System.out.println("返回值："+ Arrays.toString(download));
		IOUtils.write(download,new FileOutputStream(new File("C:\\test\\download.jpg")));
	}


	@Test
	public void testFdfsUpload() throws IOException {
		byte[] bytes = IOUtils.toByteArray(new FileSystemResource("C:\\test\\1.jpg").getInputStream());
		UploadDTO<FdfsParam> uploadDTO = new UploadDTO<>();
		uploadDTO.setFileBytes(bytes);
		uploadDTO.setSuffix("jpg");
		FdfsParam param = new FdfsParam();
		param.setFileSize((long) bytes.length);
		uploadDTO.setParam(param);
		FileResult upload = cloudStrorage.upload(uploadDTO);
		System.out.println("返回值："+ upload.getId());

		BaseFileDTO<FdfsParam> baseFileDTO = new BaseFileDTO<>();
		baseFileDTO.setParam(param);
		baseFileDTO.setId(upload.getId());
		byte[] download = cloudStrorage.download(baseFileDTO);
//		System.out.println("返回值："+ Arrays.toString(download));
		IOUtils.write(download,new FileOutputStream(new File("C:\\test\\download.jpg")));
	}


	@Test
	public void testFile() throws IOException {
		byte[] bytes = IOUtils.toByteArray(new FileSystemResource("C:\\test\\1.jpg").getInputStream());
		System.out.println(bytes.length);
	}

	@Test
	public void qiniuUpload() throws IOException {
		byte[] bytes = IOUtils.toByteArray(new FileSystemResource("C:\\test\\1.jpg").getInputStream());
		UploadDTO<QiniuParam> uploadDTO = new UploadDTO<>();
		uploadDTO.setFileBytes(bytes);
		FileResult upload = cloudStrorage.upload(uploadDTO);
		log.info(upload.toString());

		BaseFileDTO<QiniuParam> baseFileDTO = new BaseFileDTO<>();
		baseFileDTO.setId(upload.getId());
		byte[] download = cloudStrorage.download(baseFileDTO);
		IOUtils.write(download,new FileOutputStream(new File("C:\\test\\download.jpg")));
	}

}
