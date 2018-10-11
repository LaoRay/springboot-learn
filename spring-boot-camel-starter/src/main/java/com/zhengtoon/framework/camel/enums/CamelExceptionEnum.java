package com.zhengtoon.framework.camel.enums;

/**
 * @auther: qindaorong
 * @Date: 2018/6/26 15:01
 * @Description:
 */
public enum CamelExceptionEnum {

    CAN_NOT_LOAD_ROUTE("110001","can not find routeMap key, check file [routes_default.xml]");

    private String code;

    private String exceptionMessage;

    CamelExceptionEnum(String type,String exceptionMessage) {
        this.code = code;
        this.exceptionMessage = exceptionMessage;
    }


    public String getCode() {
        return code;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
