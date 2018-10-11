package com.zhengtoon.framework.exception;

import com.zhengtoon.framework.entity.CodeMessage;
import java.util.Map;

/**
 * @author 601099
 *         Copyright: Copyright (c) 2017/9/19
 * @version [1.0.0, 2017/9/19 15:16]
 *          Company: 北京通项目组
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Integer code;

    public BusinessException(Integer code, String message) {
        this(code, message, (Throwable) null);
    }

    public BusinessException(Integer code, String message, Throwable t) {
        super(message, t);
        this.code = code;
    }

    public BusinessException(Throwable t) {
        this(CoreExceptionCodes.UNKNOWN_ERROR, t);
    }

    public BusinessException(CodeMessage codeMessage) {
        this(codeMessage.getCode(), codeMessage.getMessage(), (Throwable) null);
    }

    public BusinessException(CodeMessage codeMessage, Throwable t) {
        this(codeMessage.getCode(), codeMessage.getMessage(), t);
    }

    public BusinessException(CodeMessage codeMessage, String paramValue) {
        this(codeMessage.getCode(), codeMessage.getMessage(paramValue), (Throwable) null);
    }

    public BusinessException(CodeMessage codeMessage, Throwable t, String paramValue) {
        this(codeMessage.getCode(), codeMessage.getMessage(paramValue), t);
    }

    public BusinessException(CodeMessage codeMessage, Map paramValues) {
        this(codeMessage.getCode(), codeMessage.getMessage(paramValues), (Throwable) null);
    }

    public BusinessException(CodeMessage codeMessage, Throwable t, Map paramValues) {
        this(codeMessage.getCode(), codeMessage.getMessage(paramValues), t);
    }


    public Integer getCode() {
        return this.code;
    }



}
