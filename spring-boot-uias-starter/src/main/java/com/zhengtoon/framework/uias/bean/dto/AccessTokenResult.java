package com.zhengtoon.framework.uias.bean.dto;


import lombok.Data;

/**
 * 统一身份认证返回信息
 */
@Data
public class AccessTokenResult {
    private String accessToken;
    private String refreshToken;
    private String expiresIn;
    private String scope;
}
