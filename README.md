<h1>《框架组件说明》</h1>
- [读写分离数据源配置(集成druid数据源)](#读写分离)
- [Mybatis-plus集成](#mybatis-plus集成)
- [controller模板方法](#controller层模板方法)
  - 统一异常处理
  - 统一请求入参校验
- [统一身份认证uias组件包(和jwt工具包只能二选一)](#uias组件)
- [统一云存储工具包(集成思源云、fastdfs)](#云存储工具包)
- [WEB组件包](#web组件包)
  - 集成swagger
  - 全局跨域配置
  - okHttp请求访问类
  - 全局统一的异常处理
- [disconf配置集成](#disconf配置集成)
- [基于redis的分布式锁(控制定时任务的并发)](#基于redis的分布式锁)
- [camel协议转换包](#camel协议转换包)
- [数据库domain工具包](#domain工具包)
  - 公共属性自动填充
  - 逻辑删除自动处理
- [JWT工具包(和uias工具包只能二选一)](#jwt工具包)
- [IM消息工具包](#im消息工具包)


### 读写分离
通过sharding-jdbc项目实现客户端的读写分离，只需要引入配置项即可实现相关功能，应用层代码无侵入，并且集成druid监控等功能，使用方式如下:  
* 增加依赖

```xml
<dependency>
    <groupId>com.zhengtoon.framework</groupId>
    <artifactId>spring-boot-domain-starter</artifactId>
    <version>最新版本</version>
</dependency>
```

* 配置读写分离数据源

```properties
# 数据源配置，系统中所有数据源
sharding.jdbc.datasource.names=ds_0,ds_1

# 配置具体的数据源，数据源采用druid，关于druid数据源的一些配置项可以在datasource中加入
# 第一个数据源
sharding.jdbc.datasource.ds_0.type=com.alibaba.druid.pool.DruidDataSource
sharding.jdbc.datasource.ds_0.driverClassName=com.mysql.jdbc.Driver
sharding.jdbc.datasource.ds_0.url=jdbc:mysql://127.0.0.1:3306/ds_master?useSSL=false
sharding.jdbc.datasource.ds_0.username=root
sharding.jdbc.datasource.ds_0.password=root
# 开启druid监控
sharding.jdbc.datasource.ds_0.filters=stat

# 第二个数据源(存在多个继续配置)
sharding.jdbc.datasource.ds_1.type=com.alibaba.druid.pool.DruidDataSource
sharding.jdbc.datasource.ds_1.driverClassName=com.mysql.jdbc.Driver
sharding.jdbc.datasource.ds_1.url=jdbc:mysql://127.0.0.1:3306/ds_master?useSSL=false
sharding.jdbc.datasource.ds_1.username=root
sharding.jdbc.datasource.ds_1.password=root
sharding.jdbc.datasource.ds_1.filters=stat

# 指定主从数据源（关键配置）
# 主库
sharding.jdbc.config.masterslave.masterDataSourceName=ds_0
# 从库
sharding.jdbc.config.masterslave.slaveDataSourceNames=ds_1
# 名称
sharding.jdbc.config.masterslave.name=ds_masterslave

# druid相关配置项
spring.datasource.druid.use-global-data-source-stat=true
spring.datasource.druid.web-stat-filter.url-pattern=/*
spring.datasource.druid.stat-view-servlet.url-pattern=/druid/*
spring.datasource.druid.filter.stat.log-slow-sql=true
```

> 数据的主从同步由外部的服务实现，同时需要注意同步延迟引起的数据不一致问题

**通过mapper.xml文件手写批量插入语句时，sharding-jdbc会报错，目前的sharding版本不支持，所以批量需改为单条循环插入，通过mybatisplus的批量插入并没有影响，请各位注意（重要、重要、重要）**

### 单数据源配置
如果项目中引用sharding-jdbc的依赖,单数据源的配置方式如下，如果采用spring配置数据源的方式，需要去掉sharding-jdbc的依赖

```properties
# 数据源名称
sharding.jdbc.datasource.names=ds_source
sharding.jdbc.datasource.ds_source.type=com.alibaba.druid.pool.DruidDataSource
sharding.jdbc.datasource.ds_source.driverClassName=com.mysql.jdbc.Driver
sharding.jdbc.datasource.ds_source.url=jdbc:mysql://127.0.0.1:3306/ds_demo?useSSL=false
sharding.jdbc.datasource.ds_source.username=root
sharding.jdbc.datasource.ds_source.password=root
```

### mybatis-plus集成
推荐使用Mybatis-plus来提高开发效率，属于mybaits的增强，简化开发，详细的使用方法见文档：https://github.com/baomidou/mybatis-plus  

* 项目中引入依赖

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>2.3</version>
</dependency>
```

### uias组件
spring-boot-uias-starter封装了和统一身份认证平台的交互,提供如下功能：

1. 提供了全局的SessionUtils工具类获取UserInfo用户信息对象
2. 内部封装了通用的/v1/open/getSessionId来接收toonCode参数生成sessionId的接口，从统一身份认证获取用户信息，并存在redis，默认30分钟过期
3. 提供了SessionInterceptor拦截器，可以拦截业务接口请求头中是否存在sessionId，如果不存在直接给前端返回用户已过期，存在将用户信息绑定到当前线程上


* 引入spring-boot-uias-starter包

```xml
<dependency>
    <groupId>com.zhengtoon.framework</groupId>
    <artifactId>spring-boot-uias-starter</artifactId>
    <version>最新版本</version>
</dependency>
```

* application.properties加入配置

```properties
#uias配置项
uias.info.clientId=20180622161741
uias.info.secretKey=3b20ac0ebe663d3bad5911c4c4f15a72
uias.info.accessTokenUrl=http://uias.systoon.com/auth/token
uias.info.userInfoUrl=http://uias.systoon.com/open/info/getUserInfo
uias.info.redirectUrl=http://t100cdtoon.zhengtoon.com/app/index.html

#redis配置，采用spring提供的配置方式
spring.redis.host=172.28.18.206
spring.redis.port=6379
```

> 详细内容可参考zhengtoon-framework-demo样例工程


### controller层模板方法
采用模板方法来标准化controller层统一的异常处理,即只需要处理业务逻辑,service层出现的异常转换成框架标准的BusinessException异常，然后直接向调用方抛出即可,异常由模板方法处理,例子如下

```java
@GetMapping(value = "/hello")
public ResponseResult hello(final String name) {
	return new WebResCallback() {
		@Override
		public void execute(WebResCriteria criteria, Object... params) {
			log.info("用户信息:{}",SessionUtils.getUserInfo());
			criteria.addSingleResult("Hello: "+ service.insertUser(name));
		}
	}.sendRequest(name);
}
```

* 异常统一由框架处理，业务代码只需要抛出异常即可，controller层不要拦截异常
* 接口层的入参统一由spring validate框架处理，入参的对象只需要增加validator的相关注解即可，也支持自定义注解，实例可参考demo工程，更详细的用法上网参考

> controller的接口层入参无需在增加Errors对象，返回给前端参数的异常的错误信息，由框架统一处理



### 云存储工具包
提供统一的文件操作接口，内部实现对不同云存储访问，目前接入了思源云和fastdfs两种

* 配置依赖（加入云存储包和具体底层实现依赖包）

```xml
<dependency>
    <groupId>com.zhengtoon.framework</groupId>
    <artifactId>spring-boot-cloud-storage-starter</artifactId>
    <version>最新版本</version>
</dependency>


<!--使用思源云实现，加入思源云依赖-->
<dependency>
    <groupId>com.systoon</groupId>
    <artifactId>SCloudLightSDK</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <exclusions>
        <exclusion>
            <artifactId>slf4j-log4j12</artifactId>
            <groupId>org.slf4j</groupId>
        </exclusion>
    </exclusions>
</dependency>


<!--使用fastdfs实现,加入fastdfs依赖-->
 <dependency>
    <groupId>com.github.tobato</groupId>
    <artifactId>fastdfs-client</artifactId>
    <version>1.26.2</version>
</dependency>
```

* 增加配置项

```properties

# 配置底层云存储类型(重要 fastdfs和toon)
scloud.type=toon

# 思源云存储的配置项
scloud.toon.scloud-server-host=fast.scloud.systoon.com
scloud.toon.scloud-cloud-app-id=2013
scloud.toon.scloud-assess-key-id=zqtoon
scloud.toon.scloud-access-key-secret=1c15db84e1eb4a7d8379bee65cb5b815
scloud.toon.scloud-signature=qitoon


#fastdfs的配置项
#其他的配置参考官网可提供的配置项（https://github.com/tobato/FastDFS_Client），如连接池大小配置
#服务的tracker地址配置
fdfs.tracker-list=172.28.39.151:22122
```

* 调用接口

文件上传时，不同的底层存储存在差异化的参数，所有调用的时候需要根据类型创建不同的参数对象，思源云：ToonParam 、fastdfs：FdfsParam

```java
//文件操作接口，使用时注入
@Autowired
private CloudStrorage cloudStrorage;


//用法示例
public void testToonUpload() throws IOException {
	byte[] bytes = IOUtils.toByteArray(new FileSystemResource("C:\\test\\1.jpg").getInputStream());
    //文件上传参数对象
	UploadDTO<ToonParam> uploadDTO = new UploadDTO<>();
	uploadDTO.setFileBytes(bytes);
	uploadDTO.setSuffix(".jpg");
    //底层使用是思源云，所有构造思源云参数
	ToonParam param = new ToonParam();
	param.setClientIp("192.168.1.1");
	uploadDTO.setParam(param);
	FileResult upload = cloudStrorage.upload(uploadDTO);
	System.out.println("id："+ upload.getId());
	System.out.println("url："+ upload.getUrl());
	BaseFileDTO<ToonParam> baseFileDTO = new BaseFileDTO<>();
	baseFileDTO.setParam(param);
	baseFileDTO.setId( upload.getId());
	byte[] download = cloudStrorage.download(baseFileDTO);
	//		System.out.println("返回值："+ Arrays.toString(download));
	IOUtils.write(download,new FileOutputStream(new File("C:\\test\\download.jpg")));
}
```

> 详细的使用方法可以参考jar里的单元测试类


### web组件包
提供web项目中常用的功能

* 集成swagger，应用项目无需在增加swagger配置类，只需要填写相关配置信息

```properties
#swagger配置(中文需要转码)
swagger.info.title=demo
swagger.info.description=demo
swagger.info.version=v1.0.0
#扫描的包路径,默认为com.zhengtoon
swagger.info.base-package=com.zhengtoon
# 是否开启
swagger.info.enable=true
```


* 跨域配置

已经统一处理了跨域请求，无需再进行配置

* okhttp请求
提供OkHttpClient类用于外部的http请求，集成了请求重试机制，防止在网络抖动时接口偶发异常导致请求失败，并将相关参数外部化，使用时注入OkHttpClient即可

```properties
#连接超时时间
okhttp3.connect-timeout=10
#okhttp 最大的空闲连接数
okhttp3.max-idle-connections=100
#读取超时时间
okhttp3.read-timeout=10
#写入超时时间
okhttp3.write-timeout=10
#请求接口异常时，重试的次数
okhttp3.max-retry=2
```

* 全局统一异常处理，用于处理spring抛出的异常，如400或者请求类型不对等异常

```java
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseResult globalException(Exception exption) {
		log.error(exption.getMessage(),exption);
		ResponseResult<Object> error = new ResponseResult<>();
		error.setMeta(CoreExceptionCodes.REQUEST_ERROR);
		error.setData(exption.getMessage());
		return error;
	}

}
```

### disconf配置集成
提供disconf的配置集成工具包，将配置disconf原先以xml的方式转为注解配置，并增强了spring和disconf的融合方式，支持spring以ConfigurationProperties的方式配置（重要）

> 原disconf推荐的配置方式会影响spring以ConfigurationProperties加载配置信息，对springboot的starter工具类产生影响，目前修改了spring和disconf的融合方式，暂时去掉了disconf动态更新配置项的功能（去掉了ReloadingPropertyPlaceholderConfigurer的配置）

* 拷贝标准disconf.properties文件到项目的resources目录下

* 加入依赖

```xml
<dependency>
    <groupId>com.zhengtoon.framework</groupId>
    <artifactId>spring-boot-disconf-starter</artifactId>
    <version>最新版本</version>
</dependency>
```

* 增加配置信息

```properties
# 是否开启disconf配置，默认为开启状态，如果改为false则不会启动disconf，所有的配置项默认读取本地配置
disconf.properties.enable=true
# 加载disconf上托管的配置文件，多个用逗号分隔
disconf.properties.names=redisConfig.properties,systemConfig.properties
```
* 增加启动注解类@EnableDisconfProperties，将该类增加到主类上即可

```java
@SpringBootApplication
@EnableDisconfProperties
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
```

### 基于redis的分布式锁

基于redis的分布式锁实现，控制定时任务的并发，对于定时任务采用spring Schedule + redis锁的轻量级处理替换quartz集群版

* 加入依赖

```xml
<dependency>
    <groupId>com.zhengtoon.framework</groupId>
    <artifactId>spring-boot-redislock-starter</artifactId>
    <version>最新版本</version>
</dependency>
```

* 增加redis的配置信息（springboot相关redis的配置）

```properties
spring.redis.host=172.28.18.207
spring.redis.port=6379
```

* 在需要使用同步锁的方法上添加@RedisLock注解

```java
@Slf4j
@Component
//启用定时任务（关键）
@EnableScheduling
//启用redis锁（关键）
@EnableRedisLock
public class ScheduledTask{
	/**
	 * 定时器的表达式采用配置的方式
	 * redislock的名字需要填写，不同的定时任务redislock的value需要不同
	 * @throws InterruptedException
	 */
	@Scheduled(fixedRateString = "${scheduled.mytask.fixedRate}")
	@RedisLock(value = "lock-1")
	public void scheduled() throws InterruptedException {
		log.debug("定时任务开始执行");
		TimeUnit.MILLISECONDS.sleep(5000);
	}
}
```

### camel协议转换包

封装对于apache camel 的运用，当前主要做协议转换，当前功能为HTTP转换和webservice协议转换
1. 加入依赖

```xml
<dependency>
    <groupId>com.zhengtoon.framework</groupId>
    <artifactId>spring-boot-camel-starter</artifactId>
    <version>最新版本</version>
</dependency>
```

2. 使用样例，在对应使用的Service或者Controller层引用


```java
@Autowired
CamelRouterService camelRouterService;
```


##### 发起HTTP请求
1. 起调sendHttpRequest方法。
* 参数说明：
  
    httpRequestParaDTO 发起http请求参数封装类，对应请查看【HttpRequestParaDTO】
    
* 返回值说明：

    接口统一返回集合，第三方接口返回数据统一封装在responseBody属性内
    
* 样例：

```java
HttpRequestParaDTO httpDto = new HttpRequestParaDTO();
httpDto.setParaMap(paraMap);
httpDto.setFromUrl("http://localhost:8088/hello");
httpDto.setFromMethod("get");

MessageDTO messageDTO = camelRouterService.sendHttpRequest(httpDto);
```


##### 发起webservice请求

1. 生成服务端以及接口代码并发布服务

* 配置CXF环境

    1. 去[cxf官网](http://cxf.apache.org/download.html)下载发行版CXF包，例如	apache-cxf-3.2.5.zip    

    2. 解压到本地，新建环境变量CXF_HOME D:\apache-cxf-3.2.5，在PATH中添加 %CXF_HOME%\bin


* 将.wsdl文件保存至项目根目录/wsdl/下
* 打开cmd命令行，然后进入到工程src/main/java目录下，输入如下命令生成服务端：


```
wsdl2java -server -impl D:\WorkSpace\SyswinProject\wsdl\queryService.wsdl
```

* 对应生成的CamelCXFServiceInter和对应实现类拷入项目，并记录地址


* 在pom文件中添加springboot-cxf依赖


```xml
<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-spring-boot-starter-jaxws</artifactId>
    <version>3.2.4</version>
</dependency>
```


* 编写发布服务代码

样例：


```java
@Configuration
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
}
```


* 验证服务发布成功


启动项目，浏览器输入：http://localhost:8080/services/CamelCXFService/queryService?wsdl

1. 如果生成wsdl，表示服务发布成功
2. 在.yaml文件中配置CXF接口信息
* 样例：
serviceClass 为系统生成的文件CamelCXFServiceInter全路径

wsdlLocation 为目标webservice接口生成的wsdl文件，推荐保存位置为项目根目录下/wsdl/queryService.wsdl

```
cxfserver:
    serviceClass: serviceClass=com.zhengtoon.framework.webservice.CamelCXFServiceInter
    wsdlLocation: wsdlURL=wsdl/queryService.wsdl
```

3. 起调sendWebserviceRequest方法

* 参数说明：
    
    webserviceRequestParaDTO 发起webservice请求参数封装类，对应请查看【WebserviceRequestParaDTO】
    
* 返回值说明：

    接口统一返回集合，第三方接口返回数据统一封装在responseBody属性内

* 样例：

```java
Map<String,String> paraMap = new HashMap<String, String>(1);
paraMap.put("name","leo");

WebserviceRequestParaDTO webserviceRequestParaDTO = new WebserviceRequestParaDTO();
webserviceRequestParaDTO.setParaMap(paraMap);
webserviceRequestParaDTO.setFromUrl("http://localhost:8088/CamelCXFService/queryService");
webserviceRequestParaDTO.setFromMethod("get");

MessageDTO messageDTO=camelRouterService.sendWebserviceRequest(webserviceRequestParaDTO);
```

### domain工具包

domain的工具包统一与数据库操作相关的版本依赖（sharding-jdbc、mybatis-plus、druid版本），并提供了全局的分页插件及公共属性自动填充等功能
- 公共属性自动填充功能

普通的实体类的公共属性一般有createTime（创建时间）、updateTime（更新时间）、deleteFlag（逻辑删除标识），这些属性的更新不需要通过手动操作，交由框架来统一处理

> 注意：数据库逻辑删除的列在创建表需要有默认值，设置为0（如果改成其他值，需要修改对应的配置项）,不能是null值, 该列会自动增加到查询语句当中，默认是查询所有未删除的数据

1.引用依赖

```xml
<dependency>
    <groupId>com.zhengtoon.framework</groupId>
    <artifactId>spring-boot-domain-starter</artifactId>
    <version>最新版本</version>
</dependency>
```

2.普通数据库实体类继成BaseEntity父类（数据库表中也必须存在对应列）

```java
@Data
public class BaseEntity {
	
	/**
	 * 创建时间
     * 数据保存时会自动填充
     */
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    protected Long createTime;

    /**
     * 更新时间
     * 数据保存或更新时会自动填充
     */
    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    protected Long updateTime;

    /**
     * 逻辑删除标识
     * 框架自动处理
     */
    @TableField(value = "delete_flag")
    @TableLogic
    protected Integer deleteFlag;

}
```

3.增加相关配置项

```properties
#mybatis配置
mybatis-plus.mapper-locations=classpath:/mapper/*Mapper.xml
mybatis-plus.typeAliasesPackage=com.zhengtoon.*.entity
mybatis-plus.global-config.sql-injector=com.baomidou.mybatisplus.mapper.LogicSqlInjector
#数据库表逻辑删除，1表示已删除 0代表未删除（重要）
mybatis-plus.global-config.logic-delete-value=1
mybatis-plus.global-config.logic-not-delete-value=0
#处理公共属性值自动填充的处理类（重要）
mybatis-plus.global-config.meta-object-handler=com.zhengtoon.framework.domain.PublicFieldsHandler
```

- 数据库分页功能

使用框架提供的分页查询方法时，分页逻辑由框架来处理，无需手动写分页sql，详细的操作可参考demo工程


### JWT工具包
jwt工具包提供了以下几种功能：

1. 提供将用户信息生成加密的AuthorizationToken的TokenHelper对象
2. 内部封装了通用的/v1/open/getSessionId来接收code参数生成jwtUserInfo的接口，从统一身份认证获取用户信息，并存在jwt中，默认30分钟过期
3. 提供了SessionInterceptor拦截器，可以拦截业务接口请求头中是否存在token，如果不存在直接给前端返回用户已过期
4. 如果不需要对接uias时，也可当作接口保护的功能来使用


1.调用/v1/open/getSessionId从统一身份认证获取用户信息并保存在JWT中。

将生成的token返回给前端
```json
{
  "meta": {
    "code": 0,
    "message": "成功"
  },
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySW5mbyI6IjExMSIsImlzcyI6InpoZW5ndG9vbiIsImF1ZCI6IiIsImlhdCI6MTUzMzEzMzIzMywiZXhwIjoxNTMzMTM1MDMzfQ.XCh9anBXg5TqmPZl8xJ0Yy2GSJ5Ab1QJ1dl0q84TBtvMcex-h6Sl7jg-XP4bACDqGR-UocjGAIy_WbiW_bFVzA",
    "tokenType": "bearer",
    "expires": 30
  }
}
```

2.提供JwtInterceptor拦截器，用于校验匹配的路径请求的AuthorizationToken是否合法，如果合法将token解密并将原值存在SessionUtils中

> 拦截器的使用需要调用方根据业务配置对应的拦截路径

```java
//JwtInterceptor拦截器处理逻辑
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
	JwtProperteis jwtProperteis = tokenHelper.getJwtProperteis();
	if (jwtProperteis.getIsUserUias()) {
		UserInfo userInfo = tokenHelper.getUserInfo(request);
		if (userInfo != null) {
			log.debug("会话信息内容:{}", userInfo);
			SessionUtils.setUserInfo(userInfo);
		} else {
			return this.returnFailResponse(response, JwtExceptionCodes.JWT_FAIL);
		}
	} else {
		String token = tokenHelper.getValue(JwtConstant.USERINFO_KEY, request);
		log.debug("会话信息内容:{}", token);
		if (StringUtils.isEmpty(token)) {
			return this.returnFailResponse(response, JwtExceptionCodes.TOKEN_FAIL);
		}
	}
	return true;
}
```
3.配置信息

```properties
#uias配置项
uias.info.clientId=20180622161741
uias.info.secretKey=3b20ac0ebe663d3bad5911c4c4f15a72
uias.info.accessTokenUrl=http://uias.systoon.com/auth/token
uias.info.userInfoUrl=http://uias.systoon.com/open/info/getUserInfo
uias.info.redirectUrl=http://t100cdtoon.zhengtoon.com/app/index.html

#jwt配置
#token过期时间，单位分钟
jwt.expires=30
#加密签名
jwt.secret=test
#是否使用Uias
jwt.isUserUias=true
```
4.拦截器的校验规则：匹配路径对应的请求中，请求头是否存在Authorization的key，值为bearer;token值且token是否合法，请求头类似如下：
```json
{
  "Authorization": "bearer;eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySW5mbyI6IjExMSIsImlzcyI6InpoZW5ndG9vbiIsImF1ZCI6IiIsImlhdCI6MTUzMzEzMzIzMywiZXhwIjoxNTMzMTM1MDMzfQ.XCh9anBXg5TqmPZl8xJ0Yy2GSJ5Ab1QJ1dl0q84TBtvMcex-h6Sl7jg-XP4bACDqGR-UocjGAIy_WbiW_bFVzA"
}
```
5.如果不需要对接uias，配置文件修改jwt.isUserUias=false,前端调用getSessionId接口返回的只是一个token标识,后续请求时服务端会判断这个标识是否合法，可以用来做服务端接口保护

### spring-boot-email-starter工具包
zhengtoon 根据自己的业务特点封装的用于邮件发送的starter


##### spring-boot-email-starter 使用介绍

1.在使用starter的工程下引入spring-boot-email-starter 的依赖

```java
<dependency>
    <groupId>com.zhengtoon.framework</groupId>
    <artifactId>spring-boot-email-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

2.使用样例，在对应使用的Service或者Controller层引用
```
    @Autowired
    MailService mailService;
    
    
     @RequestMapping(value = { "/sendMail" }, method = RequestMethod.GET)
     public Object sendMail(){
    
            List<String> toList = new ArrayList<String>();
            toList.add("314715382@qq.com");
            Map<String,FileSystemResource> map = new HashMap<String, FileSystemResource>();
            map.put("file",new FileSystemResource(new File("C:\\Users\\qindaorong\\Desktop\\工作文件夹.txt")));
    
            HashMap<String,String> kmap = new HashMap<String, String>();
            kmap.put("email","314715382@qq.com");
    
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setContent("test");
            emailDTO.setToList(toList);
            emailDTO.setSubject("test");
            emailDTO.setTemplate("Test");
            emailDTO.setKvMap(kmap);
            emailDTO.setFileMap(map);
    
            try {
                mailService.sendHTML(emailDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "ok";
        }
    
    
```


##### 配置参数
application.yml 中添加以下参数
```
spring:
  mail:
    host: smtp.126.com
    username: qindaorong@126.com
    password: **********
    properties:
      mail:
        smtp:
          auth: true
        starttls:
          enable: true
          required: true
```

##### 设置静态HTML模版
在/resources/static/template/路径下放置静态HTML模版。模版名称为调用参数EmailDTO中template属性值，可以有多个不同的模版，例子如下：

```
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>hello</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<p th:text="'Hello！, ' + ${email} + '!'" ></p>
</body>
</html>
```

其中${email} 为参数匹配项，将参数中对应name为email的值替换至占位符位置。


### spring-boot-kafka-producer-starter
##### spring-boot-kafka-producer-starter 功能介绍
zhengtoon 根据自己的业务特点封装的用于kafka消息发送的starter


##### spring-boot-kafka-producer-starter 使用介绍
1.在使用starter的工程下引入spring-boot-email-starter 的依赖
```
        <dependency>
            <groupId>com.zhengtoon.framework</groupId>
            <artifactId>spring-boot-kafka-producer-starter</artifactId>
            <version>1.0.0</version>
        </dependency>
```

2.使用样例，在对应使用的Service或者Controller层引用
```
    //使用时注入
    @Autowired
    private KakaProducerService kakaProducerService;
    
    
    @RequestMapping(value = { "/kafka" }, method = RequestMethod.GET)
    public Object kafka(){
        KafkaDTO kafkaDTO = new KafkaDTO();
        kafkaDTO.setTopicId("zhengtoon_topic");
        kafkaDTO.setContent("qindaorong_test_message");
        kakaProducerService.sendMessage(kafkaDTO);
        return "ok";
    }
```


##### 配置参数
在resources目录下新建kafka.properties资源文件
```
bootstrap.servers=172.28.18.46:9092,172.28.18.47:9092,172.28.18.45:9092
acks=all
retries=2
batch.size=16384
linger.ms=1
buffer.memory=33554432
key.serializer=org.apache.kafka.common.serialization.StringSerializer
value.serializer=org.apache.kafka.common.serialization.StringSerializer
```


### IM消息工具包
工具包封装和ToonIM发送消息的逻辑，提供接口和简单的传参简化调用方的复杂度，目前只支持通知类形式的消息

1.引用依赖包

```xml
<dependency>
    <groupId>com.zhengtoon.framework</groupId>
    <artifactId>spring-boot-message-starter</artifactId>
    <version>最新版本</version>
</dependency>
```

2.增加配置信息

```properties
#申请IM的appId
toon.im.app-id=850
#IM消息接口地址
toon.im.server-url=http://webserviceim.systoon.com/sendmsg
#toon的类型，102为北京通 999包type后面加999
toon.im.toon-type=102
```

3.调用发送接口

```java
//发送通知消息的接口，使用时注入
@Autowired
private ToonIMService toonIMService;


//最简使用方法
IMResult imResult = toonIMService.sendMessage(
	IMEntityFactory.createNoticeEntity(NoticeSimpleParam.builder()
			.msgID(UUID.fromString("f2420331-1f5b-4cad-9103-1ac9663f38c5"))
			.bizNo(UUID.fromString("f2420331-1f5b-4cad-9103-1ac9663f38c1"))
			.content("这是内容")
			.title("这是标题")
			.pushInfo("推送消息")
			.toClient("411382")
			.btnContent("这是按钮")
			.btnUrl("http://www.qqzhi.com/uploadpic/2015-01-18/002442661.jpg")
			.imgUrl("http://img0.bdstatic.com/static/searchresult/img/logo-2X_b99594a.png")
			.imgRedictUrl("http://img3.duitang.com/uploads/item/201409/25/20140925100559_RviGZ.jpeg").build()));

```

该工具包支持toonIM接口的所有参数，同时也支持最简传参方式，降低调用方的复杂度以满足通用的消息内容，如需更加复杂的传参可自行构造IMEntity对象，参数的详细解释参考toon的文档

> toonIM发送消息的文档：http://wiki.syswin.com/display/TOONMANUAL/%5BOpen%5D+sendmsg

4.最简使用方式

通过工具包提供的工厂方法创建消息对象

```java
//简单参数对象构造
NoticeSimpleParam noticeSimpleParam = NoticeSimpleParam.builder()....build();

//工厂方法创建IMEntity对象
IMEntityFactory.createNoticeEntity(noticeSimpleParam);
```

##### spring-boot-web-megic-starter 功能介绍
zhengtoon 根据自己的业务特点封装的爬虫load框架


##### spring-boot-web-megic-starter 使用介绍
1.在使用starter的工程下引入spring-boot-email-starter 的依赖
```
       <dependency>
            <groupId>com.zhengtoon.framework</groupId>
            <artifactId>spring-boot-web-megic-starter</artifactId>
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
        List<Spider> spiderList =processorFactory.processorList();
        return spiderList.size();
    }
```
3.processor 方法不再直接实现 PageProcessor 接口，转而继承 BaseProcessor

```
原：
XXXProcessor implements PageProcessor 

新：
XXXProcessor extends BaseProcessor 

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
    <processorElement processorName="EduInfoProcessor" requestType="post" executeClass="com.qindaorong.springboot.processor.EduInfoProcessor" rootUrl="http://www.cdcedu.cn/news/showlistList?id=e4d394da60591e6f0160591e7e9d0000" threadNumber="3">
    </processorElement>

</Processors>
```