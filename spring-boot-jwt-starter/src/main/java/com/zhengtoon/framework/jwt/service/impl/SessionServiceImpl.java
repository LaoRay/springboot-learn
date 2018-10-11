package com.zhengtoon.framework.jwt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhengtoon.framework.exception.BusinessException;
import com.zhengtoon.framework.exception.ExceptionCode;
import com.zhengtoon.framework.jwt.bean.JwtResult;
import com.zhengtoon.framework.jwt.bean.dto.AccessTokenResult;
import com.zhengtoon.framework.jwt.bean.dto.CodeDTO;
import com.zhengtoon.framework.jwt.bean.dto.UserInfo;
import com.zhengtoon.framework.jwt.config.JwtProperteis;
import com.zhengtoon.framework.jwt.security.TokenHelper;
import com.zhengtoon.framework.jwt.service.AuthService;
import com.zhengtoon.framework.jwt.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

	@Autowired
	private TokenHelper tokenHelper;

	@Autowired
	private AuthService authService;

	@Autowired
	JwtProperteis jwtProperteis;

	@Override
	public JwtResult getJwtResult(CodeDTO codeDTO) {
		if (jwtProperteis.getIsUserUias()) {
			String code = codeDTO.getCode();
			if (StringUtils.isEmpty(code)) {
				throw new BusinessException(ExceptionCode.PARAM_CODE_EMPTY);
			}
			//请求accessToken
			String accessToken = authService.getAccessToken(code);
			AccessTokenResult tokenInfo = JSONObject.parseObject(accessToken, AccessTokenResult.class);
			//用户信息，存放在JWT中
			String userInfoStr = authService.getUserInfo(tokenInfo.getAccessToken());
			UserInfo userInfo = JSON.parseObject(userInfoStr, UserInfo.class);
			userInfo.setReserved(codeDTO.getReserved());
			return tokenHelper.generateToken(JSON.toJSONString(userInfo));
		} else {
			//不需要统一身份认证,直接生成签名
			return tokenHelper.generateToken(UUID.randomUUID().toString());
		}
	}
}
