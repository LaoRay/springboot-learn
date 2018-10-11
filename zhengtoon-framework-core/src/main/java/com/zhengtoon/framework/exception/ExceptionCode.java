package com.zhengtoon.framework.exception;

import com.zhengtoon.framework.entity.CodeMessage;

/**
 * 应用异常编码
 *
 * @author
 * @since
 */
public class ExceptionCode {

    /**
     * 服务器异常
     */
    public static final CodeMessage SERVICE_BUSY = new CodeMessage(000000, "服务器繁忙，请稍后重试");

    /**
     * 系统异常
     */
    public static final CodeMessage LOW_VERSION = new CodeMessage(010000, "版本过低，当前功能不支持");

    /**
     * 接口调用异常
     */
    public static final CodeMessage INTERFACE_USE_FAILURE = new CodeMessage(100000, "接口调用失败");

    public static final CodeMessage METHOD_FAILURE = new CodeMessage(100001, "执行操作失败");


    /**
     * 基础校验--参数错误
     */
    public static final CodeMessage PARAM_IS_ILLEGAL = new CodeMessage(101001, "参数非法");
    public static final CodeMessage PARAM_CODE_EMPTY = new CodeMessage(101002, "code参数为空");

    /**
     * session, code 相关
     */
    public static final CodeMessage SESSION_IS_FAIL = new CodeMessage(200001, "获取会话失败");

    public static final CodeMessage TOKEN_EXPIRE = new CodeMessage(200002, "用户会话参数已过期");

    public static final CodeMessage TOON_CODE_EXPIRE = new CodeMessage(200003, "toonCode已过期");

    public static final CodeMessage LOGIN_TOO_MANY = new CodeMessage(200004, "登录过于频繁，请稍后尝试");

    public static final CodeMessage NO_PERSSION = new CodeMessage(200005, "sorry,您没有权限");

    /**
     * uias 异常
     */
    public static final CodeMessage UIAS_IS_FAIL = new CodeMessage(300001, "UIAS请求异常");
    public static final CodeMessage UIAS_ACCESSTOKEN_FAIL = new CodeMessage(300002, "请求accessToken异常");
    public static final CodeMessage UIAS_ACCESSTOKEN_PARAM_ERROR = new CodeMessage(300003, "参数accessToken不合法");


    /**
     * scloud 异常
     */
    public static final CodeMessage CLOUD_TYPE_FAIL = new CodeMessage(400001, "云储存类型不正确");



    /**
     * 支付通道异常
     */
    public static final CodeMessage PAY_WAY_TYPE_FAIL = new CodeMessage(500000, "支付通道设定错误");
    /**
     * pay 异常
     */
    public static final CodeMessage PAY_TYPE_FAIL = new CodeMessage(500001, "订单支付异常");
    /**
     * pay back notify 异常
     */
    public static final CodeMessage PAY_NOTIFY_FAIL = new CodeMessage(500002, "订单支付回调异常");
    /**
     * queryOrder 异常
     */
    public static final CodeMessage QUERY_ORDER_TYPE_FAIL = new CodeMessage(500003, "查询订单异常");

}