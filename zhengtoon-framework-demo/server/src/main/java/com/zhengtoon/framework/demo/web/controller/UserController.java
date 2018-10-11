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
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	//增加
	@PostMapping(value = "/insert")
	public ResponseResult insert(@RequestBody final UserDTO userDTO) {
		return new WebResCallback() {
			@Override
			public void execute(WebResCriteria criteria, Object... params) {
				criteria.addSingleResult(userService.insertUser(userDTO));
			}
		}.sendRequest(userDTO);
	}

	//删除
	@DeleteMapping(value = "/deleteById/{id}")
	public ResponseResult delete(@PathVariable final Integer id) {
		return new WebResCallback() {
			@Override
			public void execute(WebResCriteria criteria, Object... params) {
				criteria.addSingleResult(userService.deleteUser(id));
			}
		}.sendRequest(id);
	}

	//查询
	@GetMapping(value = "/selectById/{id}")
	public ResponseResult select(@PathVariable final Integer id) {
		return new WebResCallback() {
			@Override
			public void execute(WebResCriteria criteria, Object... params) {
				criteria.addSingleResult(userService.select(id));
			}
		}.sendRequest(id);
	}

	//修改
	@PutMapping(value = "/updateById")
	public ResponseResult update(@RequestBody final UserDTO userDTO) {
		return new WebResCallback() {
			@Override
			public void execute(WebResCriteria criteria, Object... params) {
				criteria.addSingleResult(userService.update(userDTO));
			}
		}.sendRequest(userDTO);
	}

	//分页操作
	@PostMapping(value = "/selectPage")
	public ResponseResult selectPage(@RequestBody final UserDTO userDTO) {
		return new WebResCallback() {
			@Override
			public void execute(WebResCriteria criteria, Object... params) {
				criteria.addSingleResult(userService.selectPage(userDTO));
			}
		}.sendRequest(userDTO);
	}

}
