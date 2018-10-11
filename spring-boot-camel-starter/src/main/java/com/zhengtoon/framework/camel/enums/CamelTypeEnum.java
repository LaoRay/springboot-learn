package com.zhengtoon.framework.camel.enums;

/**
 * @Auther: qindaorong
 * @Date: 2018/6/22 16:33
 * @Description:
 */
public enum CamelTypeEnum {
    /**
     * http
     */
    HTTP("http"),
    /**
     * webservice
     */
    WEBSERVICE("webservice");

    private String camelType;

    CamelTypeEnum(String camelType) {
        this.camelType = camelType;
    }

    public String getCamelType() {
        return camelType;
    }
}
