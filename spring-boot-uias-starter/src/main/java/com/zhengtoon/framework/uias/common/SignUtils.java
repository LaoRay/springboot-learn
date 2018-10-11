package com.zhengtoon.framework.uias.common;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Description
 * @Author lindaoliang
 * @Create 2018/3/30
 * Copyright: Copyright (c) 2018
 * Company:北京思源政务通科技有限公司
 **/
public class SignUtils {

    public static String signByMD5(TreeMap<String, String> params, String key) {
        return DigestUtils.md5Hex(String.format("%s%s", format(params), key));
    }

    private static String format(TreeMap<String, String> params) {
        StringBuilder result = new StringBuilder();
        String value;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            value = entry.getValue();
            if (value == null || "".equals(value) || "qrSign".equalsIgnoreCase(entry.getKey())) {
                continue;
            }

            result.append(entry.getKey()).append("=").append(value).append("&");
        }

        if (result.length() > 1) {
            result.deleteCharAt(result.length() - 1);
        }

        return result.toString();
    }
}
