package com.zhengtoon.framework.cloud.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResult {
	/**
	 * 文件id
	 */
	private String id;
	/**
	 * 可访问文件的url
	 */
	private String url;
}
