##### spring-boot-pay-starter功能介绍
封装主流的支付通道使用


##### spring-boot-pay-starter 使用介绍
1.在使用starter的工程下引入spring-boot-pay-starter 的依赖
```
        <dependency>
            <groupId>com.zhengtoon.framework</groupId>
            <artifactId>spring-boot-pay-starter</artifactId>
            <version>1.0.0</version>
        </dependency>
```

2.使用样例，在对应使用的Service或者Controller层引用

a.创建PayProcess类的子类，并实现方法doPayProcess()

b.记录PayProcess子类的路径并做对应配置
```
# 使用支付通道
pay.ways=wechat
weChat.config.app-id=**************
weChat.config.mch-id=**************
weChat.config.mch-key=**************
weChat.config.key-path=
weChat.config.notify-url=支付回调地址
weChat.config.redirect-url=支付重定向画面
weChat.config.process-url=PayProcess子类路径
```

3.使用微信支付，直接调用PayController中的pay方法，按照要求传递参数



