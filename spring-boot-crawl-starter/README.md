##### spring-boot-crawl-starter 功能介绍
zhengtoon 根据自己的业务特点封装的爬虫load框架


##### spring-boot-crawl-starter 使用介绍
1.在使用starter的工程下引入spring-boot-crawl-starter 的依赖
```
       <dependency>
            <groupId>com.zhengtoon.framework</groupId>
            <artifactId>spring-boot-crawl-starter</artifactId>
            <version>1.0.0</version>
        </dependency>
```

2.使用样例，在对应使用的Service或者Controller层引用

processorFactory 有多个方法，请查看API后根据需求使用，样例：
```
    @Autowired
    private ProcessorFactory processorFactory;
    
    @RequestMapping(value = { "/webmegic" }, method = RequestMethod.GET)
    public Object webmegic(){
        List<Object> list =processorFactory.processorList();
        return list.size();
    }
```
3.processor 方法不再直接实现 PageProcessor 接口，转而继承 BaseProcessor

```
使用webmegic继承 AbstractWebMegicProcessor 

使用Jsoup继承 AbstractJsoupProcessor 
```


##### 配置参数
application.yml 中添加以下参数
```
spring:
  processor:
    switch: true
```

添加配置文件 processor.xml 
```
<?xml version="1.0" encoding="UTF-8"?>
<Processors>
    <processorElement
            processorName="EduInfoProcessor"
            requestType="post"
            executeClass="com.qindaorong.springboot.processor.EduInfoProcessor"
            rootUrl="http://www.cdcedu.cn/news/showlistList?id=e4d394da60591e6f0160591e7e9d0000"
            threadNumber="3"
            processorType="1" >
    </processorElement>

    <processorElement
            processorName="PoliceAdvisoryProcessor"
            requestType="get"
            executeClass="com.qindaorong.springboot.processor.PoliceAdvisoryProcessor"
            rootUrl="www.baidu.com"
            threadNumber="3"
            processorType="2" >
    </processorElement>
</Processors>
```