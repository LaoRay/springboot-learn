package com.zhengtoon.framework.uias.common;

/**
 * 统一身份认证请求返回码
 **/
public class UiasResponseCode {
    /**
     * 成功
     */
    public static String SUCCESS_CODE = "0";

    /**
     * 参数不合法
     */
    public static int ERROR_REQUEST_PARAMS = -100;

    /**
     * 请求返回为null
     */
    public static int ERROR_RESPONSE_EMPTY = -200;

    /**
     * 获取accessToken
     */
    public static int ERROR_REQUEST_ACCESSTONKEN = -300;


    /**
     * 获取用户信息失败
     */
    public static int ERROR_REQUEST_USERINFO = -400;

    /**
     * 请求失败
     */
    public static String ERROR_REQUEST = "-500";

}