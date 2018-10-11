package com.zhengtoon.framework.pay.bean.dto.wxpay;

import com.zhengtoon.framework.pay.enums.WxPayTypeEnum;
import lombok.Data;

import java.io.Serializable;


@Data
public class WxPayRequest implements Serializable {

    private static final long serialVersionUID = 7773243688412445025L;

    /**
     * 支付方式.
     */
    private String wxPayTypeEnum  = WxPayTypeEnum.WXPAY_MWEB.getType();

    /**
     * 客户端访问Ip  外部H5支付时必传，需要真实Ip
     */
    private String spbillCreateIp;
}
