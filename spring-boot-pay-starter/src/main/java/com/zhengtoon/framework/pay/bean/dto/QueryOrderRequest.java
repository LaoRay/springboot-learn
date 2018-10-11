package com.zhengtoon.framework.pay.bean.dto;

import com.zhengtoon.framework.pay.enums.PayTypeEnum;
import lombok.Data;

/**
 * @author: qindaorong
 * @Date: 2018/10/3 09:50
 * @Description:
 */
@Data
public class QueryOrderRequest {

    /**
     * 商户系统内部订单号
     */
    private String outTradeNo;

    /**
     * 支付通道
     */
    private PayTypeEnum payTypeEnum =  PayTypeEnum.weChat;

    /**
     * 微信的订单号
     */
    private String transactionId;
}
