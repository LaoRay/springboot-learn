package com.zhengtoon.framework.pay.components.impl.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhengtoon.framework.pay.bean.dto.AbstractPayParam;
import com.zhengtoon.framework.pay.bean.dto.BasePayResponse;
import com.zhengtoon.framework.pay.bean.dto.QueryOrderRequest;
import com.zhengtoon.framework.pay.bean.dto.wxpay.*;
import com.zhengtoon.framework.pay.components.PayService;
import com.zhengtoon.framework.pay.components.SpringApplicationContext;
import com.zhengtoon.framework.pay.config.PayConfig;
import com.zhengtoon.framework.pay.constants.WxPayConstants;
import com.zhengtoon.framework.pay.process.PayProcess;
import com.zhengtoon.framework.pay.utils.*;
import com.zhengtoon.framework.utils.HttpUtils;
import com.zhengtoon.framework.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
public class WxPayService implements PayService {

    private PayConfig.WeChatConfig weChatConfig;

    private PayProcess payProcess;

    public WxPayService(PayConfig payConfig){
        this.weChatConfig = payConfig.getConfig();
        this.initPayProcess();
    }

    private void initPayProcess(){
        try {
            Class processClazz = Class.forName(weChatConfig.getProcessUrl());
            Object object = SpringApplicationContext.getBean(processClazz);
            if(object instanceof PayProcess){
                payProcess = (PayProcess)object;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BasePayResponse pay(AbstractPayParam<?> payParam) {
        log.debug("[WxPayService] [pay] request payParam  json is [{}]",JsonUtil.toString(payParam));
        Object o  = payParam.getT();
        WxPayRequest wxPayRequest = JSONObject.parseObject(JSONObject.toJSONString(o),WxPayRequest.class);

        WxPayUnifiedOrderRequest wxRequest = new WxPayUnifiedOrderRequest();
        wxRequest.setOutTradeNo(payParam.getOrderNo());
        wxRequest.setTotalFee(MoneyUtil.yuan2Fen(payParam.getOrderAmount()));
        wxRequest.setBody(payParam.getOrderName());
        wxRequest.setTradeType(wxPayRequest.getWxPayTypeEnum());
        wxRequest.setAppId(weChatConfig.getAppId());
        wxRequest.setMchId(weChatConfig.getMchId());
        wxRequest.setNotifyUrl(weChatConfig.getNotifyUrl());
        wxRequest.setNonceStr(RandomUtils.getRandomStr());
        wxRequest.setSpbillCreateIp(wxPayRequest.getSpbillCreateIp());
        wxRequest.setSceneInfo(payParam.getSceneInfo());
        wxRequest.setSign(WxPaySignature.sign(MapUtils.buildMap(wxRequest), weChatConfig.getMchKey()));

        String wxRequestStr = XmlUtil.toString(wxRequest);
        log.debug("[WxPayServiceImpl][pay] ready to work：WxPayUnifiedOrderRequest={}", wxRequestStr);
        String result = HttpUtils.syncPostString(WxPayConstants.UNIFIED_ORDER_URL, null, wxRequestStr);

        WxPaySyncResponse response = (WxPaySyncResponse) XmlUtil.toObject(result, WxPaySyncResponse.class);
        log.debug("[WxPayServiceImpl][pay] is worked [WxPaySyncResponse]={}", XmlUtil.toString(response));

        Boolean isCorrect = this.isReturnCodeAndResultCodeCorrect(response.getReturnCode(),response.getResultCode());
        if(!isCorrect){
            throw new RuntimeException("[WxPayServiceImpl][pay] is finished but has error!");
        }else{
            payProcess.paySuccessProcess(response);
        }

        if (StringUtils.isNotBlank(weChatConfig.getRedirectUrl())) {
            try {
                String  encodeUrl = URLEncoder.encode(weChatConfig.getRedirectUrl(), WxPayConstants.UTF8);
                response.setMwebUrl(response.getMwebUrl().concat("&redirect_url=").concat(encodeUrl));
            } catch (UnsupportedEncodingException e) {
                log.error("[WxPayServiceImpl][pay] response has error ! message is :[{}]",e.getMessage());
            }
        }
        return response;
    }

    @Override
    public void asyncNotify(String notifyData){
        log.error("[WxPayServiceImpl] [asyncNotify] notifyData={}", notifyData);
        //verify signature
        if (!WxPaySignature.verify(XmlUtil.xmlToMap(notifyData), weChatConfig.getMchKey())) {
            log.error("[WxPayServiceImpl] [asyncNotify] verify signature failed , response={}", notifyData);
            throw new RuntimeException("[WxPayServiceImpl] [asyncNotify] verify signature failed!");
        }

        WxPayAsyncResponse asyncResponse = (WxPayAsyncResponse) XmlUtil.toObject(notifyData, WxPayAsyncResponse.class);

        if (!WxPayConstants.SUCCESS.equals(asyncResponse.getReturnCode()) ) {
            log.error("[WxPayServiceImpl]has error! [returnCode] is {} ",asyncResponse.getReturnCode());
            throw new RuntimeException("[WxPayServiceImpl][asyncNotify] is finished but has error!");
        }

        Boolean isPaid = this.isPaid(asyncResponse.getErrCode());
        if(isPaid){
            log.warn("[WxPayServiceImpl][asyncNotify] orderNo:[{}] has bean paid! [transaction_id] :[{}]",asyncResponse.getOutTradeNo(),asyncResponse.getTransactionId());
        }

        payProcess.notifyPaySuccessProcess(asyncResponse);
    }

    @Override
    public BasePayResponse queryOrder(QueryOrderRequest queryOrderRequest) {
        WxPayQueryOrderRequest wxPayQueryOrderRequest = new WxPayQueryOrderRequest();
        wxPayQueryOrderRequest.setAppId(weChatConfig.getAppId());
        wxPayQueryOrderRequest.setMchId(weChatConfig.getMchId());
        wxPayQueryOrderRequest.setTransactionId(queryOrderRequest.getTransactionId());
        wxPayQueryOrderRequest.setOutTradeNo(queryOrderRequest.getOutTradeNo());
        wxPayQueryOrderRequest.setNonceStr(RandomUtils.getRandomStr());
        wxPayQueryOrderRequest.setSign(WxPaySignature.sign(MapUtils.buildMap(wxPayQueryOrderRequest), weChatConfig.getMchKey()));
        log.debug("[WxPayServiceImpl][queryOrder] request Param：WxPayQueryOrderRequest={}", JSON.toJSONString(wxPayQueryOrderRequest, true));

        String wxPayQueryOrderRequestStr = XmlUtil.toString(wxPayQueryOrderRequest);
        String result = HttpUtils.syncPostString(WxPayConstants.ORDER_QUERY, null, wxPayQueryOrderRequestStr);
        log.debug("[WxPayServiceImpl][queryOrder] response={}", result);

        WxQuerySyncResponse querySyncResponse = (WxQuerySyncResponse) XmlUtil.toObject(result, WxQuerySyncResponse.class);
        log.debug("[WxPayServiceImpl][queryOrder] querySyncResponse={}", querySyncResponse);
        return querySyncResponse;
    }


    private Boolean isReturnCodeAndResultCodeCorrect(String returnCode,String resultCode){
        if (!WxPayConstants.SUCCESS.equals(returnCode) || !WxPayConstants.SUCCESS.equals(resultCode)) {
            log.error("[WxPayServiceImpl][isReturnCodeAndResultCodeCorrect] has error! [returnCode] is {} ,[resultCode] is {}",returnCode,resultCode);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private Boolean isPaid(String errCode){
        if (WxPayConstants.ORDER_PAID.equals(errCode)) {
            log.error("[WxPayServiceImpl][isPaid] Order is paid!");
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}