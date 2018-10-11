package com.zhengtoon.framework.pay.bean.dto.wxpay;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Leiqiyun
 * @date 2018/9/30 10:33
 */
@Data
@Root(name = "xml", strict = false)
public class WxPayQueryOrderRequest {

    @Element(name = "appid")
    private String appId;

    @Element(name = "mch_id")
    private String mchId;

    @Element(name = "transaction_id", required = false)
    private String transactionId;

    @Element(name = "out_trade_no", required = false)
    private String outTradeNo;

    @Element(name = "nonce_str")
    private String nonceStr;

    @Element(name = "sign")
    private String sign;

    @Element(name = "sign_type", required = false)
    private String signType;
}
