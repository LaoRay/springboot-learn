package com.zhengtoon.framework.pay;

import com.zhengtoon.framework.pay.components.impl.wechat.WxPayService;
import com.zhengtoon.framework.pay.config.PayConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan({"com.zhengtoon.framework.pay","com.zhengtoon.framework.pay.bean.dto.*"})
@EnableConfigurationProperties(PayConfig.class)
@Slf4j
public class PayWayConfigure {

    @Autowired
    WxPayService wxPayService;

    @Value("${pay.ways:}")
    String payWays;

    @Bean
    public PayWay createPayService() {
        log.debug("[WxPayService] need to create [payWays]!");
        if(wxPayService != null){
            log.debug("[WxPayService] has bean create!");
        }
        return new PayWay(payWays);
    }

    @Data
    public class PayWay{
        String[] payWayArray;

        public PayWay(String payWays){
            payWayArray = payWays.split(",");
        }
    }
}