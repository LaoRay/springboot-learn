package com.zhengtoon.framework.domain;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;


/**
 * 公共字段处理类，自动填充更新时间和创建时间
 *
 * @author LuCheng.Qi
 * @since 2018-07-18
 * Company:北京思源政务通有限公司
 */
public class PublicFieldsHandler extends MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		long time = System.currentTimeMillis();
		setFieldValByName("createTime", time, metaObject);
		setFieldValByName("updateTime", time, metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		setFieldValByName("updateTime", System.currentTimeMillis(), metaObject);
	}
}
