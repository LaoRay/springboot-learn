package com.zhengtoon.framework.demo.common.exception;


import com.zhengtoon.framework.entity.CodeMessage;

/**
 * 应用异常编码
 */
public class ExceptionCode {

    /**
     * 服务器异常
     */
    public static final CodeMessage SERVICE_BUSY = new CodeMessage(999999, "服务器繁忙，请稍后重试");


}