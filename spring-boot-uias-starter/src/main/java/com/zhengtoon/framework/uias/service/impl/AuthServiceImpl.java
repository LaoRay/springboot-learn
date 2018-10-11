package com.zhengtoon.framework.uias.service.impl;


import com.alibaba.fastjson.JSON;
import com.zhengtoon.framework.entity.ResponseResult;
import com.zhengtoon.framework.exception.BusinessException;
import com.zhengtoon.framework.exception.ExceptionCode;
import com.zhengtoon.framework.uias.common.AppConf;
import com.zhengtoon.framework.uias.common.SignUtils;
import com.zhengtoon.framework.uias.common.UiasResponseCode;
import com.zhengtoon.framework.uias.configuration.UiasInfoConfig;
import com.zhengtoon.framework.uias.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UiasInfoConfig appInfoConfig;


    @Autowired
    private OkHttpClient okHttpClient;

    @Override
    public String getUserInfo(String accessToken) {
        log.debug("accessToken:{}",appInfoConfig);
        if(StringUtils.isBlank(accessToken)){
            throw new BusinessException(ExceptionCode.UIAS_ACCESSTOKEN_FAIL);
        }
        if (accessToken.length() != AppConf.ACCESSTOKEN_LENGTH) {
            throw new BusinessException(ExceptionCode.UIAS_ACCESSTOKEN_PARAM_ERROR);
        }
        Request.Builder builder = new Request.Builder()
                .url(appInfoConfig.getUserInfoUrl() + "?accessToken=" + accessToken).get();
        return this.call(builder.build());
    }

    @Override
    public String getAccessToken(String code) {
        appInfoConfig.setGrantType(AppConf.GRANT_TYPE_AUTHORIZATION_CODE);
        log.debug("应用配置信息[{}]",appInfoConfig);
        TreeMap<String, String> requestMap = new TreeMap<>();
        requestMap.put("code", code);
        requestMap.put("clientId", appInfoConfig.getClientId());
        requestMap.put("grantType", appInfoConfig.getGrantType());
        requestMap.put("redirectUri", appInfoConfig.getRedirectUrl());
        requestMap.put("clientSecret", appInfoConfig.getSecretKey());
        String sign = SignUtils.signByMD5(requestMap, "");
        requestMap.put("sign", sign);
        requestMap.remove("clientSecret");
        //封装请求参数
        FormBody.Builder formBuilder = new FormBody.Builder();
        for(Map.Entry<String,String> entry  : requestMap.entrySet()){
            formBuilder.add(entry.getKey(),entry.getValue());
        }
        Request.Builder builder = new Request.Builder()
                .url(appInfoConfig.getAccessTokenUrl())
                .post(formBuilder.build());

        return this.call(builder.build());
    }


    /**
     * 请求调用
     *
     * @param request 请求
     * @return 返回值
     */
    private String call(Request request) {
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                if (StringUtils.isBlank(result) || JSON.parseObject(result).isEmpty() ) {
                    throw new BusinessException(ExceptionCode.UIAS_IS_FAIL);
                }
                ResponseResult responseResult = JSON.parseObject(result, ResponseResult.class);
                if (UiasResponseCode.SUCCESS_CODE.equals(responseResult.getMeta().getCode().toString())) {
                    //请求成功返回内容
                    return JSON.toJSONString(responseResult.getData());
                }
                log.error("Uias请求异常:{}",result);
            }
        } catch (IOException e) {
            log.error("Request exception .", e);
        }
        throw new BusinessException(ExceptionCode.UIAS_IS_FAIL);
    }
}
