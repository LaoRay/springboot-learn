package com.zhengtoon.framework.cloud.service;


import com.zhengtoon.framework.cloud.bean.dto.BaseFileDTO;
import com.zhengtoon.framework.cloud.bean.dto.FileResult;
import com.zhengtoon.framework.cloud.bean.dto.UploadDTO;

/**
 * 文件操作统一入口
 */
public interface CloudStrorage {

	/**
	 * 文件上传
	 *
	 * @param uploadFileDTO 文件参数对象
	 * @return 文件ID
	 */
	FileResult upload(UploadDTO uploadFileDTO);


	/**
	 * 文件下载
	 *
	 * @return 文件信息
	 */
	byte[] download(BaseFileDTO downloadDTO);


	/**
	 * 删除文件
	 * 思源云：删除租户引用，不删除源文件
	 *
	 * @param baseFileDTO 删除参数
	 * @return 处理结果
	 */
	boolean delete(BaseFileDTO baseFileDTO);

}
