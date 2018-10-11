##### zhengtoon-framework-task 功能介绍
使用动态添加的方式完成对于项目定时任务处理，每个任务执行都有对应的redis同步锁完成独立运行。


##### zhengtoon-framework-task-starter 使用介绍
1.在application.yml中添加redis链接相关信息
```
# starter 配置
zhengtoon:
  framework:
    taskManageAddress: 127.0.0.1:8080
    taskManageUserName: zhengtoon
    taskManagePassword: zhengtoon.framework
```


2.在第三方后台使用starter的工程下引入zhengtoon-framework-task-starter 的依赖，后配置相关资源文件，开始task调度
```
        <dependency>
            <groupId>com.zhengtoon.framework</groupId>
            <artifactId>zhengtoon-framework-task-starter</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

3.使用样例，在需要使用task调度的文件中引入TaskManagerServer，并使用
```
     @Autowired
     TaskManagerServer taskManagerServer;
        
     @RequestMapping(value = { "/addNewTask" }, method = RequestMethod.GET)
     public void addNewTask(){
         TaskDTO taskDTO = new TaskDTO ();
         taskDTO.setExecuteUrl("http://localhost:8080/healthCheck");
         taskDTO.setTaskCron("0 55 12 * * ?");
         taskDTO.setTaskGroup("qidnaorong_test");
         taskDTO.setTaskName("qidnaorong_healthCheckTask");
 
         ServerHttpResponse serverHttpResponse = taskManagerServer.addNewTask(taskDTO);
         if(serverHttpResponse.getHttpStatus() == HttpStatus.SC_OK){
             System.out.print("addNewTask over");
         }
     }       

```


##### zhengtoon-framework-task-core 介绍
独立工程可以使用war或者jar形式独立运行，实现动态添加task。 

并通过使用redis锁实现任务单一触发。 

使用数据库完成task任务共享和加载。 



##### zhengtoon-framework-task-core 配置文件说明
application.yml 

1、添加redis相关属性。 

2、druid连接池相关属性。 

3、mybatis-plus相关属性。