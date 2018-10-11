##### spring-boot-camel-starter 功能介绍
封装对于apache camel 的运用，当前主要做协议转换，当前功能为HTTP转换和webservice协议转换


##### spring-boot-camel-starter 使用介绍
1.在使用starter的工程下引入spring-boot-camel-starter 的依赖
```
        <dependency>
            <groupId>com.zhengtoon.framework</groupId>
            <artifactId>spring-boot-camel-starter</artifactId>
            <version>1.0.0</version>
        </dependency>
```

2.使用样例，在对应使用的Service或者Controller层引用
```
    @Autowired
    CamelRouterService camelRouterService;
```


##### 发起HTTP请求
1.起调sendHttpRequest方法。
* 参数说明：
  
    httpRequestParaDTO 发起http请求参数封装类，对应请查看【HttpRequestParaDTO】
    
* 返回值说明：

    接口统一返回集合，第三方接口返回数据统一封装在responseBody属性内
    
* 样例：
```
        HttpRequestParaDTO httpDto = new HttpRequestParaDTO();
        httpDto.setParaMap(paraMap);
        httpDto.setFromUrl("http://localhost:8088/hello");
        httpDto.setFromMethod("get");

        MessageDTO messageDTO = camelRouterService.sendHttpRequest(httpDto);
```


##### 发起webservice请求

1.生成服务端以及接口代码并发布服务

* 配置CXF环境

    1. 去[cxf官网](http://cxf.apache.org/download.html)下载发行版CXF包，例如	apache-cxf-3.2.5.zip    

    2. 解压到本地，新建环境变量CXF_HOME D:\apache-cxf-3.2.5，在PATH中添加 %CXF_HOME%\bin


* 将.wsdl文件保存至项目根目录/wsdl/下
* 打开cmd命令行，然后进入到工程src/main/java目录下，输入如下命令生成服务端：

`
wsdl2java -server -impl D:\WorkSpace\SyswinProject\wsdl\queryService.wsdl
`
* 对应生成的CamelCXFServiceInter和对应实现类拷入项目，并记录地址


* 在pom文件中添加springboot-cxf依赖
```
<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-spring-boot-starter-jaxws</artifactId>
    <version>3.2.4</version>
</dependency>
```
* 编写发布服务代码

样例：
```@Configuration
   public class CxfConfig {
       @Autowired
       private Bus bus;
       @Autowired
       CamelCXFServiceInter camelCXFServiceInter;
  
      @Bean
      public Endpoint endpoint() {
          EndpointImpl endpoint = new EndpointImpl(bus, camelCXFServiceInter);
          endpoint.publish("/CamelCXFService/queryService");
          return endpoint;
      }

```
* 验证服务发布成功

启动项目，浏览器输入：http://localhost:8080/services/CamelCXFService/queryService?wsdl

如果生成wsdl，表示服务发布成功

2.在.yaml文件中配置CXF接口信息
* 样例：
serviceClass 为系统生成的文件CamelCXFServiceInter全路径

wsdlLocation 为目标webservice接口生成的wsdl文件，推荐保存位置为项目根目录下/wsdl/queryService.wsdl

```
cxfserver:
    serviceClass: serviceClass=com.zhengtoon.framework.webservice.CamelCXFServiceInter
    wsdlLocation: wsdlURL=wsdl/queryService.wsdl

```
3.起调sendWebserviceRequest方法。
* 参数说明：
    
    webserviceRequestParaDTO 发起webservice请求参数封装类，对应请查看【WebserviceRequestParaDTO】
    
* 返回值说明：

    接口统一返回集合，第三方接口返回数据统一封装在responseBody属性内
* 样例：
```
        Map<String,String> paraMap = new HashMap<String, String>(1);
        paraMap.put("name","leo");

        WebserviceRequestParaDTO webserviceRequestParaDTO = new WebserviceRequestParaDTO();
        webserviceRequestParaDTO.setParaMap(paraMap);
        webserviceRequestParaDTO.setFromUrl("http://localhost:8088/CamelCXFService/queryService");
        webserviceRequestParaDTO.setFromMethod("get");
        
        MessageDTO messageDTO=camelRouterService.sendWebserviceRequest(webserviceRequestParaDTO);
```