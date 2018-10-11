package com.zhengtoon.framework.uias.controller;

import com.zhengtoon.framework.entity.ResponseResult;
import com.zhengtoon.framework.uias.bean.dto.CodeDTO;
import com.zhengtoon.framework.uias.service.SessionService;
import com.zhengtoon.framework.web.common.WebResCallback;
import com.zhengtoon.framework.web.common.WebResCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 统一身份认证通用接口
 */
@RestController
@RequestMapping("/v1/open")
@Slf4j
@Api(value = "会话管理", description = "会话管理")
public class SessionController {

	@Autowired
	private SessionService sessionService;

	@ApiOperation(value = "获取会话id", httpMethod = "POST", notes = "获取会话id")
	@RequestMapping(value = "/getSessionId", method = RequestMethod.POST)
	public ResponseResult getSessionId(
			@ApiParam(required = true, name = "code", value = "code对象")
			@Valid @RequestBody final CodeDTO codeDTO) {
		return new WebResCallback() {
			@Override
			public void execute(WebResCriteria criteria, Object... params) {
				criteria.addSingleResult(sessionService.getSessionIdByCode(codeDTO));
			}
		}.sendRequest(codeDTO);
	}


	@ApiOperation(value = "清除会话id", httpMethod = "GET", notes = "清除会话id")
	@RequestMapping(value = "/invalidSessionId/{id}", method = RequestMethod.GET)
	public ResponseResult invalidSessionId(
			@ApiParam(required = true, name = "id", value = "会话id")
			@PathVariable("id") final String sessionId) {
		return new WebResCallback() {
			@Override
			public void execute(WebResCriteria criteria, Object... params) {
				sessionService.invalidSessionId(sessionId);
			}
		}.sendRequest();
	}

}
