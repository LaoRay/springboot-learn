package com.zhengtoon.framework.uias.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhengtoon.framework.uias.bean.dto.AccessTokenResult;
import com.zhengtoon.framework.uias.bean.dto.CodeDTO;
import com.zhengtoon.framework.uias.bean.dto.UserInfo;
import com.zhengtoon.framework.uias.configuration.UiasInfoConfig;
import com.zhengtoon.framework.uias.service.AuthService;
import com.zhengtoon.framework.uias.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

	@Autowired
	private AuthService authService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private UiasInfoConfig appInfoConfig;

	private final String uiasRedisKey = "uias:session:";


	@Override
	public UserInfo getUserInfoBySessionId(String sessionId) {
		BoundValueOperations<String, String> boundValueOps = stringRedisTemplate.boundValueOps(uiasRedisKey + sessionId);
		if (boundValueOps != null) {
			String userStr = boundValueOps.get();
			//增加续时逻辑
			boundValueOps.expire(appInfoConfig.getSessionTimeOut(), TimeUnit.SECONDS);
			return JSON.parseObject(userStr, UserInfo.class);
		}
		return null;
	}

	@Override
	public void invalidSessionId(String sessionId) {
		stringRedisTemplate.delete(uiasRedisKey + sessionId);
	}

	@Override
	public String getSessionIdByCode(CodeDTO code) {
		//请求accessToken
		String accessToken = authService.getAccessToken(code.getCode());
		AccessTokenResult tokenInfo = JSONObject.parseObject(accessToken, AccessTokenResult.class);
		//用户信息，存放在redis里
		String userInfoStr = authService.getUserInfo(tokenInfo.getAccessToken());
		UserInfo userInfo = JSON.parseObject(userInfoStr, UserInfo.class);
		String sessionId = UUID.randomUUID().toString();
		userInfo.setSessionId(sessionId);
		userInfo.setReserved(code.getReserved());
		stringRedisTemplate.boundValueOps(uiasRedisKey + sessionId).set(JSON.toJSONString(userInfo),
				appInfoConfig.getSessionTimeOut(), TimeUnit.MINUTES);
		return sessionId;
	}

}
