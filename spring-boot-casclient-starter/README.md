##### spring-boot-casclient-starter 功能介绍
zhengtoon 为了业务使用方便封装单点登录客户端的starter


##### spring-boot-casclient-starter 使用介绍

1.在使用starter的工程下引入spring-boot-casclient-starter 的依赖
```
        <dependency>
            <groupId>com.zhengtoon.framework</groupId>
            <artifactId>spring-boot-casclient-starter</artifactId>
            <version>1.0.0</version>
        </dependency>
```
2.使用样例
在启动类上添加注解启动自动装配
```
@SpringBootApplication
@EnableCasClient//开启cas
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

```

##### 配置参数
application.properties 中添加以下参数
```
cas.server-url-prefix=https://server.cas.com:8443/cas
cas.server-login-url=https://server.cas.com:8443/cas/login
cas.client-host-url=http://app.cas.com:8082

以上域名均为测试域名 实际开发以实际开发环境为准
```

