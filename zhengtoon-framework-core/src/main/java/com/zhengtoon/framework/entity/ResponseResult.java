package com.zhengtoon.framework.entity;

import com.zhengtoon.framework.exception.BusinessException;
import com.zhengtoon.framework.exception.CoreExceptionCodes;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 返回码
     */
    private CodeMessage meta = new CodeMessage();
    /**
     * 返回的数据
     */
    private T data;// 数据

    public ResponseResult(T data) {
        this(CoreExceptionCodes.SUCCESS.getCode(), CoreExceptionCodes.SUCCESS.getMessage(), data);
    }

    public ResponseResult() {
        super();
    }

    public void setMeta(CodeMessage meta) {
        this.meta = meta;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseResult(Integer code, String message) {
        this(code, message, null);
    }

    public ResponseResult(Integer code, String message, T data) {
        this.meta.setCode(code);
        this.meta.setMessage(message);
        this.data = data;
    }

    public ResponseResult(BusinessException be) {
        this(be.getCode(), be.getMessage(), null);
    }

    public ResponseResult(CodeMessage codeMessage) {
        this(codeMessage.getCode(), codeMessage.getMessage(), null);
    }

    /**
     *
     * 包装成功返回的对象
     *
     * @author 137127
     * @param data
     *            数据对象
     * @return ResponseResult<T> 返回的结果对象
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(data);
    }

    /**
     *
     * 包装错误返回结果
     *
     * @author 137127
     * @param code
     *            错误编码
     * @param message
     *            错误信息
     * @return ResponseResult<T> 返回的结果对象
     */
    public static <T> ResponseResult<T> fail(Integer code, String message) {
        return new ResponseResult<>(code, message);
    }

    public T getData() {
        return data;
    }

    public CodeMessage getMeta() {
        return meta;
    }

    /**
     *
     * 判断返回的结果编码是否正确
     *
     * @author 137127
     * @return boolean 结果是否成功
     */
    public boolean checkSuccess() {
        return null != this.meta && CoreExceptionCodes.SUCCESS.getCode().equals(meta.getCode());
    }

}
