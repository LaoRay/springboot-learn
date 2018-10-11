##### spring-boot-redisson-starter 功能介绍
基于redis做的同步锁start，设想使用场景为并发task环境下，解决多个定时任务同时启动问题。


##### spring-boot-redisson-starter 使用介绍
1.在application.yml中添加redis链接相关信息
```
# redis
spring:
    redis:
          database: 0
          host: xxx.xx.xxx.xx
          port: 6379
          password:
```


2.在使用starter的工程下引入spring-boot-distributed-lock-starter 的依赖
```
        <dependency>
            <groupId>com.zhengtoon.framework</groupId>
            <artifactId>spring-boot-redisson-starter</artifactId>
            <version>1.1.0</version>
        </dependency>
```

3.使用样例，在需要使用同步锁的上添加@RedisLock标签
```
    @RequestMapping(value = "/redlock")
    @RedisLock
    public String testRedlock() throws Exception {
        System.out.println("get lock");
        return "redlock";
    }
```