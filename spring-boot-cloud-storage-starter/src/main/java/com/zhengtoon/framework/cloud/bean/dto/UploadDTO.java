package com.zhengtoon.framework.cloud.bean.dto;

import com.zhengtoon.framework.cloud.bean.dto.param.FileParam;
import lombok.Data;

/**
 * 文件上传对象
 * @param <T> 不同存储差异化参数
 *           1. 思源云创建ToonParam
 *           2. fastdfs创建fdfsParam
 */
@Data
public class UploadDTO<T extends FileParam> {

	/**
	 * 文件字节数组
	 */
	private byte[] fileBytes;
	/**
	 * 文件后缀
	 * 1. 思源云扩展名需要带. 如.jpg
	 * 2. fastdfs不要要带.  如jpg
	 */
	private String suffix;
	/**
	 * 不同底层，不同的参数对象
	 */
	private T param;
}
