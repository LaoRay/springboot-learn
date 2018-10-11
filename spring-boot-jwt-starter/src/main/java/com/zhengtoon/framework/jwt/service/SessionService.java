package com.zhengtoon.framework.jwt.service;

import com.zhengtoon.framework.jwt.bean.JwtResult;
import com.zhengtoon.framework.jwt.bean.dto.CodeDTO;

public interface SessionService {

	/**
	 * 生成jwt信息
	 * @param codeDTO code对象
	 * @return jwt对象
	 */
	JwtResult getJwtResult(CodeDTO codeDTO);
}
