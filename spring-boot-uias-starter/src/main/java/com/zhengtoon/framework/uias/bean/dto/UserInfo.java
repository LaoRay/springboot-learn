package com.zhengtoon.framework.uias.bean.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 用户信息实体
 **/
@Data
@ToString
public class UserInfo {
    private String certLevel;
    private String certName;
    private String certNo;
    private String mail;
    private String mobile;
    private String sex;
    private String toonNo;
    private String uniqueId;
    private String userId;
    private String userName;
    private String userType;
    private String version;
    private String jsession;
    private String token;
    private String priKey;
    private String pubKey;
    private String sessionId;
    //预留字段和用户信息绑定
    private String reserved;
}
