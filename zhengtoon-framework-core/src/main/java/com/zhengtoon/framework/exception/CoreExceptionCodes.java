package com.zhengtoon.framework.exception;

import com.zhengtoon.framework.entity.CodeMessage;

import java.io.Serializable;

/**
 * @ClassName UtilsExceptionCodes
 * @Description: 工具类型异常编码;编码规则: 业务系统前缀（3~5位大写字母，与项目英文简称相同）
 * +2位错误类别（认证权限类10、参数错误类20，错误类别00~59为系统保留，60~99由业务自行定义） +3位错误编码
 * @author: 601099
 * @since: 2018年5月28日18:20:43
 */
public class CoreExceptionCodes implements Serializable{
    CoreExceptionCodes() {
    }

    /**
     * 成功
     */
    public static final CodeMessage SUCCESS = new CodeMessage(0, "成功");

    /**
     * 系统异常
     */
    public static final CodeMessage UNKNOWN_ERROR = new CodeMessage(999999, "系统异常");

    /**
     * 前端请求异常
     */
    public static final CodeMessage REQUEST_ERROR = new CodeMessage(1000000, "系统请求异常");

    /**
     * 参数为空 10001
     */
    public static final CodeMessage PARAM_IS_NULL = new CodeMessage(20001, "参数为空");
    /**
     * 参数非法 10002
     */
    public static final CodeMessage PARAM_IS_ILLEGAL = new CodeMessage(20002, "参数非法");

    /***************** 60 段加密错误信息 start ***********************/
    /**
     * 加密算法不存在 60001
     */
    public static final CodeMessage ALGORITHM_IS_ILLEGAL = new CodeMessage(60001, "加密算法不存在");
    /**
     * 加密算法不存在 60002
     */
    public static final CodeMessage BOUNCY_CASTLE_PROVIDER_NO_EXIS = new CodeMessage(60002, "加密算法不存在");

    /**
     * 加密方法失败 60003
     */
    public static final CodeMessage ENCRYPT_FAIL = new CodeMessage(60003, "加密失败");
    /**
     * 解密方法失败 60004
     */
    public static final CodeMessage DECRYPT_FAIL = new CodeMessage(60004, "解密失败");

    /**
     * 加密文件关闭失败 60005
     */
    public static final CodeMessage ENCRYPT_FILE_CLOSE_FAIL = new CodeMessage(60005, "加密文件关闭失败");
    /**
     * 解密文件关闭失败 60006
     */
    public static final CodeMessage DECRYPT_FILE_CLOSE_FAIL = new CodeMessage(60006, "解密文件关闭失败");

    /**
     * DES的key生成失败 60007
     */
    public static final CodeMessage DES_UTIS_KEY_GEN_ERROR = new CodeMessage(60007, "DES的key生成失败 ");

    /**
     * SHA256加密方法失败 60008
     */
    public static final CodeMessage ENCRYPT_SHA_256_FAIL = new CodeMessage(60008, "SHA256加密失败");
    /**
     * SHA256加密方法失败 60009
     */
    public static final CodeMessage ENCRYPT_DES_FAIL = new CodeMessage(60009, "DES加密失败");
    /**
     * SHA256加密方法失败 60010
     */
    public static final CodeMessage DECRYPT_DES_FAIL = new CodeMessage(60010, "DES解密失败");

    /**
     * RSA的key生成失败 60011
     */
    public static final CodeMessage RSA_UTIS_KEY_GEN_ERROR = new CodeMessage(60011, "RSA的key生成失败 ");

    /**
     * 60012 RSA的获取Cipher失败
     */
    public static final CodeMessage RSA_CIPHER_GET_FAIL = new CodeMessage(60012, "RSA的获取Cipher失败 ");
    /**
     * RSA生成秘钥对失败  60013
     */
    public static final CodeMessage RSA_GENERATOR_KEY_FAIL = new CodeMessage(60013, "RSA生成秘钥对失败 ");

    /**
     * RSA加密方法失败 60014
     */
    public static final CodeMessage ENCRYPT_RSA_FAIL = new CodeMessage(60014, "RSA加密失败");
    /**
     * RSA加密方法失败 60015
     */
    public static final CodeMessage DECRYPT_RSA_FAIL = new CodeMessage(60015, "RSA解密失败");


    /***************** 60 段加密错误信息 end ***********************/

    /***************** 61段加密错误信息 start ***********************/

    /**
     * 61000 Http请求返回码错误
     */
    public static final CodeMessage HTTP_CODE_WRONG = new CodeMessage(61000, "Http请求返回码错误");
    /***************** 61段加密错误信息 end ***********************/

    /***************** 62段加密错误信息 start ***********************/
    /**
     * 对象拷贝失败 62000
     */
    public static final CodeMessage CACHE_BEAN_FAIL = new CodeMessage(62000, "对象拷贝失败");
    /***************** 62段加密错误信息 end ***********************/

}
