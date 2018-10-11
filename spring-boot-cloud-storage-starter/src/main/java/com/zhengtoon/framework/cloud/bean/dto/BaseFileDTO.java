package com.zhengtoon.framework.cloud.bean.dto;

import com.zhengtoon.framework.cloud.bean.dto.param.FileParam;
import lombok.Data;

/**
 * 基本对象
 * @param <T> 存在差异参数
 *            1. 思源云创建ToonParam
 *            2. fastdfs创建fdfsParam
 */
@Data
public class BaseFileDTO<T extends FileParam> {
	/**
	 * 文件ID
	 */
	private String id;
	/**
	 * 不同底层，不同的参数对象
	 */
	private T param;
	
}
