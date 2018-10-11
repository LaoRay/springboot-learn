package com.zhengtoon.framework.pay.components;


import com.zhengtoon.framework.pay.bean.dto.AbstractPayParam;
import com.zhengtoon.framework.pay.bean.dto.BasePayResponse;
import com.zhengtoon.framework.pay.bean.dto.QueryOrderRequest;

public interface PayService {

	/**
	 * 发起支付
	 *
	 * @param payParam payParam
	 * @return BasePayResponse 支付结果通知
	 */
    BasePayResponse pay(AbstractPayParam<?> payParam);

	/**
	 * 支付异步回调
	 *
	 * @param notifyData
	 */
	void asyncNotify(String notifyData);

	/**
	 * 查询订单
	 *
	 * @param queryOrderRequest queryOrderRequest
	 * @return BasePayResponse 支付结果通知
	 */
	BasePayResponse queryOrder(QueryOrderRequest queryOrderRequest);
}