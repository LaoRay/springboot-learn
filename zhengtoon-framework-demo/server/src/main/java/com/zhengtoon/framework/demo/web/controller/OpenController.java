package com.zhengtoon.framework.demo.web.controller;

import com.zhengtoon.framework.demo.bean.dto.UserDTO;
import com.zhengtoon.framework.demo.service.UserService;
import com.zhengtoon.framework.entity.ResponseResult;
import com.zhengtoon.framework.web.common.WebResCallback;
import com.zhengtoon.framework.web.common.WebResCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/open")
@Slf4j
public class OpenController {

	@Autowired
	private UserService userService;

	//查询
	@PostMapping(value = "/selectById")
	public ResponseResult select(@RequestBody final UserDTO userDTO) {
		return new WebResCallback() {
			@Override
			public void execute(WebResCriteria criteria, Object... params) {
				criteria.addSingleResult(
						userService.select(userDTO.getId())
				);
			}
		}.sendRequest(userDTO);
	}

}
