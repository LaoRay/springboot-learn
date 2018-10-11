package com.zhengtoon.framework.jwt.bean;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResult {
	private String accessToken;
	private String tokenType;
	private int expires;
}
