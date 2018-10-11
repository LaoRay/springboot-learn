package com.zhengtoon.framework.jwt.common;


import com.zhengtoon.framework.jwt.bean.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;

/**
 *  用户信息工具类
 */
@Slf4j
public class SessionUtils {

    private SessionUtils() {
    }

    private static final ThreadLocal<UserInfo> LOCAL = new ThreadLocal<>();

    public static void setUserInfo(UserInfo userInfo) {
        LOCAL.set(userInfo);
    }

    public static UserInfo getUserInfo() {
        return LOCAL.get();
    }

    static void deleteUserInfo() {
        if (LOCAL.get() != null) {
            LOCAL.remove();
        }
    }

}
