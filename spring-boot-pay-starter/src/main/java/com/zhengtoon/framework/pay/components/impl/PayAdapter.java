package com.zhengtoon.framework.pay.components.impl;

import com.zhengtoon.framework.exception.BusinessException;
import com.zhengtoon.framework.exception.ExceptionCode;
import com.zhengtoon.framework.pay.bean.dto.AbstractPayParam;
import com.zhengtoon.framework.pay.bean.dto.BasePayResponse;
import com.zhengtoon.framework.pay.bean.dto.QueryOrderRequest;
import com.zhengtoon.framework.pay.components.impl.wechat.WxPayService;
import com.zhengtoon.framework.pay.enums.PayTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author: qindaorong
 * @Date: 2018/9/30 15:18
 * @Description:
 */
@Component
@Slf4j
public class PayAdapter {

    @Autowired
    @Lazy
    WxPayService wxPayService;

    public BasePayResponse pay(AbstractPayParam<?> payParam) {
        if(PayTypeEnum.weChat.equals(payParam.getPayTypeEnum())){
            try {
                return wxPayService.pay(payParam);
            } catch (Exception e) {
                new BusinessException(ExceptionCode.PAY_TYPE_FAIL);
            }
        }else{
            throw new BusinessException(ExceptionCode.PAY_WAY_TYPE_FAIL);
        }
        return null;
    }

    public void weChatAsyncNotify(String notifyData) {
        log.debug("[PayAdapter][weChatAsyncNotify] is back!");
        try {
            wxPayService.asyncNotify(notifyData);
        } catch (Exception e) {
            new BusinessException(ExceptionCode.PAY_NOTIFY_FAIL);
        }
    }


    public BasePayResponse queryOrder(QueryOrderRequest queryOrderRequest) {

        if(PayTypeEnum.weChat.equals(queryOrderRequest.getPayTypeEnum())){
            try {
                return wxPayService.queryOrder(queryOrderRequest);
            } catch (Exception e) {
                new BusinessException(ExceptionCode.QUERY_ORDER_TYPE_FAIL);
            }
        }else{
            throw new BusinessException(ExceptionCode.PAY_WAY_TYPE_FAIL);
        }
        return null;
    }
}
