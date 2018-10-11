package com.zhengtoon.framework.demo.bean.dto;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhengtoon.framework.demo.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends Page<User> {

	private Integer id;

	private String name;

	private Integer age;

	private String email;

	private String nationality;

	private String education;

}
