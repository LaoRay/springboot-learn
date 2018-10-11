package com.zhengtoon.framework.uias.service;


import com.zhengtoon.framework.uias.bean.dto.CodeDTO;
import com.zhengtoon.framework.uias.bean.dto.UserInfo;


public interface SessionService {

	String getSessionIdByCode(CodeDTO code);

	UserInfo getUserInfoBySessionId(String sessionId);

	void invalidSessionId(String sessionId);

}
