package com.zhengtoon.framework.pay.constants;

/**
 * @author leiqiyun
 */
public interface WxPayConstants {

    String SUCCESS = "SUCCESS";

    String OK = "OK";

    String UTF8 = "UTF8";

    /**
     * 统一下单接口
     */
    String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 查询订单
     */
    String ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 订单已支付
     */
    String ORDER_PAID = "ORDERPAID";
}
