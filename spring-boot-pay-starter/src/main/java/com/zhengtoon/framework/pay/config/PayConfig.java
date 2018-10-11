package com.zhengtoon.framework.pay.config;

import com.zhengtoon.framework.pay.enums.PayTypeEnum;
import com.zhengtoon.framework.pay.components.impl.wechat.WxPayService;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "weChat")
public class PayConfig {

    private WeChatConfig config = new WeChatConfig();

    @Data
    public static class WeChatConfig{
        private String appId;
        private String mchId;
        private String mchKey;
        private String keyPath;
        private String notifyUrl;
        private String redirectUrl;
        private String processUrl;
        private PayTypeEnum payType = PayTypeEnum.weChat;
    }

    @Bean
    public WxPayService createWxPayServiceImpl(){
        return new WxPayService(this);
    }

}
