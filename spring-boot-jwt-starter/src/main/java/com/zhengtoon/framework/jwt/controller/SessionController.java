package com.zhengtoon.framework.jwt.controller;

import com.zhengtoon.framework.entity.ResponseResult;
import com.zhengtoon.framework.jwt.bean.dto.CodeDTO;
import com.zhengtoon.framework.jwt.service.SessionService;
import com.zhengtoon.framework.web.common.WebResCallback;
import com.zhengtoon.framework.web.common.WebResCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * jwt通用接口
 */
@Slf4j
@RestController
@RequestMapping("/v1/open")
@Api(value = "会话管理",description = "会话管理")
public class SessionController {

	@Autowired
	private SessionService sessionService;

	@ApiOperation(value = "获取会话id", httpMethod = "POST", notes = "获取会话id")
	@RequestMapping(value = "/getSessionId", method = RequestMethod.POST)
	public ResponseResult getSessionId(final
									   @ApiParam(name = "code", value = "uias的code对象")
									   @RequestBody CodeDTO codeDTO) {
		return new WebResCallback() {
			@Override
			public void execute(WebResCriteria criteria, Object... params) {
				criteria.addSingleResult(sessionService.getJwtResult(codeDTO));
			}
		}.sendRequest();
	}
}
