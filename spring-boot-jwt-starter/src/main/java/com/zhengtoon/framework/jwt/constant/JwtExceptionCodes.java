package com.zhengtoon.framework.jwt.constant;

import com.zhengtoon.framework.entity.CodeMessage;

import java.io.Serializable;

public class JwtExceptionCodes implements Serializable{

    /**
     * JWTtoken过期
     */
    public static final CodeMessage JWT_FAIL = new CodeMessage(401, "Authorization token已失效");

    /**
     * JWTtoken不合法
     */
    public static final CodeMessage TOKEN_FAIL = new CodeMessage(401, "非法请求");

}
