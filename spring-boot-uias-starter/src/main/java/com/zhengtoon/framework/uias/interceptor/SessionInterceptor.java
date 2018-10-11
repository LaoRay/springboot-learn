package com.zhengtoon.framework.uias.interceptor;

import com.alibaba.fastjson.JSON;
import com.zhengtoon.framework.entity.ResponseResult;
import com.zhengtoon.framework.exception.ExceptionCode;
import com.zhengtoon.framework.uias.bean.dto.UserInfo;
import com.zhengtoon.framework.uias.common.SessionUtils;
import com.zhengtoon.framework.uias.service.SessionService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@NoArgsConstructor
public class SessionInterceptor extends HandlerInterceptorAdapter {

    private SessionService sessionService;

    public SessionInterceptor(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * 在请求处理之前进行调用 获取用户信息
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
        String sessionId= request.getHeader("sessionId");
        log.debug("拦截请求url = {} ,会话的sessionId:{}",request.getRequestURI() ,sessionId);
        if(StringUtils.isBlank(sessionId)){
            return this.returnFailResponse(response);
        }
        UserInfo userInfo = sessionService.getUserInfoBySessionId(sessionId);
        if(userInfo == null){
            return this.returnFailResponse(response);
        }
        SessionUtils.setUserInfo(userInfo);
        return true;
    }

    /**
     * 返回失败的信息
     * @param response 返回对象
     * @return false
     * @throws IOException IOException
     */
    private boolean returnFailResponse(HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "text/json;charset=UTF-8");
        response.getWriter().print(JSON.toJSON(new ResponseResult<>(ExceptionCode.TOKEN_EXPIRE)));
        return false;
    }

}
