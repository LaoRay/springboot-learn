package com.zhengtoon.framework.jwt.interceptor;

import com.alibaba.fastjson.JSON;
import com.zhengtoon.framework.entity.CodeMessage;
import com.zhengtoon.framework.entity.ResponseResult;
import com.zhengtoon.framework.jwt.bean.dto.UserInfo;
import com.zhengtoon.framework.jwt.common.SessionUtils;
import com.zhengtoon.framework.jwt.config.JwtProperteis;
import com.zhengtoon.framework.jwt.constant.JwtConstant;
import com.zhengtoon.framework.jwt.constant.JwtExceptionCodes;
import com.zhengtoon.framework.jwt.security.TokenHelper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@NoArgsConstructor
public class JwtInterceptor extends HandlerInterceptorAdapter {

	private TokenHelper tokenHelper;

	public JwtInterceptor(TokenHelper tokenHelper) {
		this.tokenHelper = tokenHelper;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		JwtProperteis jwtProperteis = tokenHelper.getJwtProperteis();
		if (jwtProperteis.getIsUserUias()) {
			UserInfo userInfo = tokenHelper.getUserInfo(request);
			if (userInfo != null) {
				log.debug("会话信息内容:{}", userInfo);
				SessionUtils.setUserInfo(userInfo);
			} else {
				return this.returnFailResponse(response, JwtExceptionCodes.JWT_FAIL);
			}
		} else {
			String token = tokenHelper.getValue(JwtConstant.USERINFO_KEY, request);
			log.debug("会话信息内容:{}", token);
			if (StringUtils.isEmpty(token)) {
				return this.returnFailResponse(response, JwtExceptionCodes.TOKEN_FAIL);
			}
		}
		return true;
	}

	/**
	 * 返回失败的信息
	 *
	 * @param response 返回对象
	 * @return false
	 * @throws IOException IOException
	 */
	private boolean returnFailResponse(HttpServletResponse response, CodeMessage codeMessage) throws IOException {
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().print(JSON.toJSON(new ResponseResult<>(codeMessage)));
		return false;
	}

}
