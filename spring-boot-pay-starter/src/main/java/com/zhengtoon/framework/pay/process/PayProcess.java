package com.zhengtoon.framework.pay.process;

import com.zhengtoon.framework.pay.bean.dto.wxpay.WxPayAsyncResponse;
import com.zhengtoon.framework.pay.bean.dto.wxpay.WxPaySyncResponse;

/**
 * @author: qindaorong
 * @Date: 2018/9/30 15:48
 * @Description:
 */
public abstract class PayProcess {


    /**
     * 微信支付完成操作
     * @param response
     */
    public abstract void paySuccessProcess(WxPaySyncResponse response);

    /**
     * 支付完成回调后续操作
     * @param asyncResponse
     */
    public abstract void notifyPaySuccessProcess(WxPayAsyncResponse asyncResponse);
}
