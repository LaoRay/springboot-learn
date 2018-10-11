package com.zhengtoon.framework.camel.exceptions;

/**
 * @auther: qindaorong
 * @Date: 2018/6/26 13:42
 * @Description:
 */
public class ZhengtoonCamelException extends RuntimeException  {

    private String code;

    private static final long serialVersionUID = -375805702767069545L;

    public ZhengtoonCamelException() { }

    public ZhengtoonCamelException(String message) {
        super(message);
    }

    public ZhengtoonCamelException(String code, String message) {
        super(message);
        this.code = code;
    }

}
