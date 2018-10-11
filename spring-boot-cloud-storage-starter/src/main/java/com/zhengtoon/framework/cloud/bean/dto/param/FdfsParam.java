package com.zhengtoon.framework.cloud.bean.dto.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
public class FdfsParam extends FileParam {
	/**
	 * 文件大小
	 */
	private long fileSize;
}
