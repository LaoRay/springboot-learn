package com.zhengtoon.framework.exception;

import com.zhengtoon.framework.entity.CodeMessage;

/**
 * @author 601099
 * @ClassName SDKExceptionCodes
 * @Description 工具类型异常编码;编码规则: 业务系统前缀（3~5位大写字母，与项目英文简称相同）
 * +2位错误类别（认证权限类10、参数错误类20，错误类别00~59为系统保留，60~99由业务自行定义） +3位错误编码
 * @since 2018年5月28日18:27:24
 */
public class SDKExceptionCodes extends CoreExceptionCodes {

    /************** 60 ceritficate start ******************/
    /**
     * 60001 获取oauth的accessToken失败
     */
    public static final CodeMessage CERTIFICATE_ACCESSTOKEN_GET_FAIL = new CodeMessage(60001,
            "获取Certificate accessToken失败");

    /**
     * 60002 刷新oauth的token失败
     */
    public static final CodeMessage CERTIFICATE_REFRESHTOKEN_GET_FAIL = new CodeMessage(60002,
            "刷新Certificate oauth的token失败");

    /**
     * 60003 获取实名认证oauth的appToken失败
     */
    public static final CodeMessage CERTIFICATE_APPTOKEN_GET_FAIL = new CodeMessage(60003, "获取实名认证appToken失败");

    /**
     * 60004 获取实名认证用户信息失败
     */
    public static final CodeMessage CERTIFICATE_USER_GET_FAIL = new CodeMessage(60004, "获取实名认证用户信息失败");

    /************** 60 ceritficate end ******************/
    /************** 61 message start ******************/

    /**
     * 61000 发送通知失败
     */
    public static final CodeMessage SEND_IM_MESSAGE_FAIL = new CodeMessage(61000, "发送通知消息失败");

    /**
     * 61001 发送短信失败
     */
    public static final CodeMessage SEND_MESSAGE_FAIL = new CodeMessage(61001, "发送短信失败");

    /************** 61 message end ******************/
    /************** 62 storage start ******************/
    /**
     * 62000 下载文件失败
     */
    public static final CodeMessage CLOUD_DOWNLOAD_FAIL = new CodeMessage(62000, "下载文件失败");
    /**
     * 62001 下载文件失败
     */
    public static final CodeMessage CLOUD_DOWNLOAD_URL_FAIL = new CodeMessage(62001, "获取下载路径失败");

    /**
     * 62002 FTP登录失败
     */
    public static final CodeMessage FTP_LOGIN_FAIL = new CodeMessage(62002, "FTP登录失败");
    /**
     * 62003 FTP登录失败
     */
    public static final CodeMessage FTP_UPLOAD_FAIL = new CodeMessage(62003, "FTP上传失败");

    /**
     * 62004 上传文件失败
     */
    public static final CodeMessage CLOUD_UPLOAD_FAIL = new CodeMessage(62004, "上传文件失败");

    /************** 62 storage end ******************/
    /************** 63 toon start ******************/
    /**
     * 63002 生成key失败
     */
    public static final CodeMessage GENERATE_KEY_FAIL = new CodeMessage(63002, "生成key失败");

    /**
     * 63003 解析ToonCode失败
     */
    public static final CodeMessage RESOLVE_CODE_FAIL = new CodeMessage(63003, "解析ToonCode失败");

    /**
     * 63004 获取手机号失败
     */
    public static final CodeMessage GET_USER_TEL_FAIL = new CodeMessage(63004, "获取手机号失败");

    /**
     * 63005 获取Feed列表失败
     */
    public static final CodeMessage GET_FEED_LIST_FAIL = new CodeMessage(63005, "获取Feed列表失败");
    /**
     * 63006 路由解析错误
     */
    public static final CodeMessage TOON_ROUTE_FAIL = new CodeMessage(63006, "路由解析错误");

    /**
     * 63007 获取toon的accessToken失败
     */
    public static final CodeMessage TOON_ACCESSTOKEN_GET_FAIL = new CodeMessage(63007, "获取toon_accessToken失败");

    /**
     * 63008 注册应用接口（个人&群组）失败
     */
    public static final CodeMessage TOON_REGISTERED_GET_FAIL = new CodeMessage(63008, "注册应用接口（个人&群组）失败");

    /**
     * 63009 注册应用接口（组织&员工）失败
     */
    public static final CodeMessage TOON_ORG_REGISTERED_GET_FAIL = new CodeMessage(63009, "注册应用接口（组织&员工）失败");

    /**
     * 63010 查询员工名片详情失败
     */
    public static final CodeMessage TOON_STAFFCARD_GET_FAIL = new CodeMessage(63010, "查询员工名片详情失败");

    /**
     * 63011 管理员添加应用失败
     */
    public static final CodeMessage TOON_ORGR_EGISTERED_GET_FAIL = new CodeMessage(63011, "管理员添加应用失败");

    /**
     * 63012 增量查询员工名片列表失败
     */
    public static final CodeMessage TOON_ORGR_LIST_GET_FAIL = new CodeMessage(63012, " 增量查询员工名片列表失败");

    /**
     * 63013 删除应用接口失败
     */
    public static final CodeMessage TOON_ORGR_EGISTERED_DEL_FAIL = new CodeMessage(63013, " 删除应用接口失败");

    /**
     * 63014根据公司ID查询公司名片feedId失败
     */
    public static final CodeMessage TOON_ORGR_COMID_GET_FAIL = new CodeMessage(63014, " 根据公司ID查询公司名片feedId失败");

    /**
     * 63015 查询公司名片详情失败
     */
    public static final CodeMessage TOON_ORGR_GET_FAIL = new CodeMessage(63015, "查询一个或多个公司名片详情");


    /**
     * 63016 查询用户信息(手机号)失败
     */
    public static final CodeMessage GET_USER_INFO_FAIL = new CodeMessage(63016, "获取手机号失败");
    /**
     * 63017 内容审核发送请求失败
     */
    public static final CodeMessage TOON_CONTENT_CENSOR_FAIL = new CodeMessage(63017, "发送内容审核消息失败");
    /**
     * 63018 未设置oauth应用client_id
     */
    public static final CodeMessage OAUTH_CLIENT_ID_NOT_SET = new CodeMessage(63018, "未设置oauth应用client_id");

    private SDKExceptionCodes() {
        super();
    }


    /************** 63 toon end ******************/

}
