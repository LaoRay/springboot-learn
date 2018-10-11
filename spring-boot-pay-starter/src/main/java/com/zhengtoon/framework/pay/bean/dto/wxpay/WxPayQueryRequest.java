package com.zhengtoon.framework.pay.bean.dto.wxpay;

import lombok.Data;

/**
 * @author Leiqiyun
 * @date 2018/9/30 10:59
 */
@Data
public class WxPayQueryRequest{

    /**
     * 微信的订单号
     */
    private String transactionId;
}
