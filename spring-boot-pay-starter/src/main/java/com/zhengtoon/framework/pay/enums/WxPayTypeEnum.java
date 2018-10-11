package com.zhengtoon.framework.pay.enums;

public enum WxPayTypeEnum {

    /**
     * 微信H5支付
     */
    WXPAY_MWEB("MWEB");

    private String type;


    WxPayTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
