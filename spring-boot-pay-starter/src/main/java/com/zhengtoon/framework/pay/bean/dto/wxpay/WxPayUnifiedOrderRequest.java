package com.zhengtoon.framework.pay.bean.dto.wxpay;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author leiqiyun
 */
@Data
@Root(name = "xml", strict = false)
public class WxPayUnifiedOrderRequest {

    @Element(name = "appid")
    private String appId;

    @Element(name = "mch_id")
    private String mchId;

    @Element(name = "nonce_str")
    private String nonceStr;

    @Element(name = "sign")
    private String sign;

    @Element(name = "sign_type", required = false)
    private String signType;

    @Element(name = "attach", required = false)
    private String attach;

    @Element(name = "body", required = false)
    private String body;

    @Element(name = "detail", required = false)
    private String detail;

    @Element(name = "notify_url")
    private String notifyUrl;

    @Element(name = "openid", required = false)
    private String openid;

    @Element(name = "out_trade_no")
    private String outTradeNo;

    @Element(name = "spbill_create_ip")
    private String spbillCreateIp;

    @Element(name = "total_fee")
    private Integer totalFee;

    @Element(name = "trade_type")
    private String tradeType;

    @Element(name = "scene_info")
    private String sceneInfo;
}
