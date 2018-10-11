package com.zhengtoon.framework.pay.controller;

import com.zhengtoon.framework.entity.ResponseResult;
import com.zhengtoon.framework.pay.bean.dto.AbstractPayParam;
import com.zhengtoon.framework.pay.bean.dto.QueryOrderRequest;
import com.zhengtoon.framework.pay.components.impl.PayAdapter;
import com.zhengtoon.framework.pay.constants.WxPayConstants;
import com.zhengtoon.framework.pay.utils.XmlUtil;
import com.zhengtoon.framework.web.common.WebResCallback;
import com.zhengtoon.framework.web.common.WebResCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leiqiyun
 * @date 2018/9/28 10:20
 */
@RestController
@Slf4j
@RequestMapping("/v1/open")
@Api(value = "支付controller" ,description ="支付接口API")
public class PayController {

    @Autowired
    private PayAdapter payAdapter;

    @ApiOperation(value = "起发起调用支付通道接口", notes = "起发起调用支付通道接口")
    @PostMapping("/pay")
    public ResponseResult pay(@RequestBody final AbstractPayParam<?> payParam) {
        return new WebResCallback() {
            @Override
            public void execute(WebResCriteria criteria, Object... params) {
                criteria.addSingleResult(payAdapter.pay(payParam));
            }
        }.sendRequest();
    }

    /**
     * weChat支付异步回调
     */
    @PostMapping("/weChat/notify")
    @SneakyThrows
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        log.debug("[PayController][notify] is back!");
        String notifyData = XmlUtil.parseXmlToString(request);
        log.debug("[PayController][notify] notifyData={}",notifyData);
        payAdapter.weChatAsyncNotify(notifyData);
        response.getWriter().write(XmlUtil.setXML(WxPayConstants.SUCCESS, WxPayConstants.OK));
    }

    /**
     * 查询订单
     *
     * @param queryOrderRequest
     * @return
     */
    @ApiOperation(value = "查询订单支付信息接口", notes = "查询订单支付信息接口")
    @PostMapping("/queryOrder")
    public ResponseResult queryOrder(@RequestBody final QueryOrderRequest queryOrderRequest) {
        return new WebResCallback() {
            @Override
            public void execute(WebResCriteria criteria, Object... params) {
                criteria.addSingleResult(payAdapter.queryOrder(queryOrderRequest));
            }
        }.sendRequest();
    }
}
