##### spring-boot-email-starter 功能介绍
zhengtoon 根据自己的业务特点封装的用于邮件发送的starter


##### spring-boot-email-starter 使用介绍
1.在使用starter的工程下引入spring-boot-email-starter 的依赖
```
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