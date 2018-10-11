package com.zhengtoon.framework.cloud.bean.dto.param;

import com.zhengtoon.framework.cloud.bean.enums.FilePublicEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
public class ToonParam extends FileParam{
	/**
	 * 客户端ip
	 */
	private String clientIp;
	/**
	 * sdk解释为客户端的经纬度，具体说明不详 ，用默认值
	 */
	private String location = "111.132,123.123";
	/**
	 * 是否公开，默认为公开
	 */
	private FilePublicEnum isPublic = FilePublicEnum.PUBLIC;
}
