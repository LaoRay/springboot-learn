package com.zhengtoon.framework.demo.bean.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserVO{

    private Integer id;

    private String name;

    private Integer age;

    private String email;

    private String nationality;

    private String education;
}
