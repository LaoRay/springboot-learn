package com.zhengtoon.framework.demo.web.controller;

import com.zhengtoon.framework.entity.ResponseResult;
import com.zhengtoon.framework.jwt.constant.JwtConstant;
import com.zhengtoon.framework.jwt.security.TokenHelper;
import com.zhengtoon.framework.web.common.WebResCallback;
import com.zhengtoon.framework.web.common.WebResCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/jwtdemo")
@Slf4j
public class DemoController {

	@Autowired
	private TokenHelper tokenHelper;

	@GetMapping(value = "/say")
	public ResponseResult say(final String name) {
		return new WebResCallback() {
			@Override
			public void execute(WebResCriteria criteria, Object... params) {
				criteria.addSingleResult(tokenHelper.generateToken(name));
			}
		}.sendRequest(name);
	}


	@GetMapping(value = "/hello")
	public ResponseResult hello(final HttpServletRequest request) {
		return new WebResCallback() {
			@Override
			public void execute(WebResCriteria criteria, Object... params) {
				criteria.addSingleResult(request.getAttribute(JwtConstant.USERINFO_KEY));
			}
		}.sendRequest();
	}


}
