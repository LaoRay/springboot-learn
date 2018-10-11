
##### zhengtoon-framework-task-manager-starter 使用介绍
1.准备工作
* 创建task表
````
CREATE TABLE `task` (
`id`  int(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
`task_cron`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`execute_url`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`task_group`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`task_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;
````
* 引入依赖
```
 <dependency>
    <groupId>com.zhengtoon.framework</groupId>
    <artifactId>zhengtoon-framework-task-manager-starter</artifactId>
    <version>1.0.0</version>
 </dependency>
```
* 根据项目需要在resources资源文件根目录创建task.xml

```
例：
<?xml version="1.0" encoding="UTF-8"?>
<tasks>
    
    <task id="edu" taskName="edu" taskCron="0 0 22 * * ?" executeUrl="" taskGroup="edu" />
    <task id="civic" taskName="civic" taskCron="0 0 22 * * ?" executeUrl="" taskGroup="civic" />
   
</tasks>
```

2.如何使用
* 实现 TaskManageService 接口，根据项目需求编写对task表的增删改查实现方法

3.原理

项目启动时会先检查resources/task.xml是否存在，然后会先查询DB中task表，并读入内存中并开始job，若task.xml存在，则和DB比较，若DB中没有此ID的task，则读入内存中开始job