package com.zhengtoon.framework.pay.bean.dto;

import com.zhengtoon.framework.pay.enums.PayTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: qindaorong
 * @Date: 2018/9/30 10:51
 * @Description:
 */
@Data
public class AbstractPayParam<T> implements Serializable {

    private static final long serialVersionUID = 406304551290333060L;

    /**
     * 订单号.
     */
    private String orderNo;

    /**
     * 订单金额.
     */
    private BigDecimal orderAmount;

    /**
     * 订单名字.
     */
    private String orderName;

    /**
     * 支付场景说明.
     */
    private String sceneInfo;

    /**
     * 支付通道
     */
    private PayTypeEnum payTypeEnum;


    /**
     * 支付实体类
     */
    private T t;
}
