package zhengtoon.framework.task.core.components;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import zhengtoon.framework.task.core.dto.TaskCase;
import zhengtoon.framework.task.core.enums.JobContextKeyEnum;
import zhengtoon.framework.task.core.service.TaskManageServer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther: qindaorong
 * @Date: 2018/7/16 11:16
 * @Description:
 */
@Component
@Import(TaskFactory.class)
public class TaskFactory implements ImportBeanDefinitionRegistrar {

    private static final Logger log = LoggerFactory.getLogger(TaskFactory.class);

    private static final String BEAN_NAME = "taskFactory";
    private static final String EXECUTE_CLASS = "zhengtoon.framework.task.core.components.JobTask";

    @Autowired
    private Scheduler scheduler;

    @Autowired
    @Lazy
    private TaskManageServer taskManageServer;

    private static ConcurrentHashMap<String, TaskCase> concurrentMap = new ConcurrentHashMap<String, TaskCase>();

    @PostConstruct
    private void init(){
        log.info("start load DB task to memory!");
        List<TaskCase> taskCaseList = taskManageServer.queryTasks();
        if(!CollectionUtils.isEmpty(taskCaseList)){
            for(TaskCase taskCase : taskCaseList){
                this.add(taskCase);
            }
        }else{
            log.info("DB is empty! no nead to load!");
        }
        log.info("finish load DB task to memory!");
    }

    public Boolean isExist(String taskName){
        if(concurrentMap.containsKey(taskName)){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    public Boolean add(TaskCase task){
        String taskName = task.getTaskName();
        Boolean isNew = Boolean.FALSE;
        if(!isExist(taskName)){
            concurrentMap.put(taskName, task);
            this.startJob(taskName);
            isNew = Boolean.TRUE;
        }
        return isNew;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(BEAN_NAME)) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(TaskFactory.class);
            beanDefinition.setSynthetic(true);
            registry.registerBeanDefinition(BEAN_NAME, beanDefinition);
        }
    }

    public void startJob(String taskName){

        TaskCase task = concurrentMap.get(taskName);
        try {

            log.info("start load Task to [Quartz], taskName is [{}]",taskName);
            JobKey jobKey = new JobKey(taskName, task.getTaskGroup());
            Class jobClazz = Class.forName(EXECUTE_CLASS);

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put(JobContextKeyEnum.JOB_CONTEXT_KEY.getKey(), task);

            JobDetail jobDetail = JobBuilder.newJob(jobClazz)
                    .withIdentity(jobKey)
                    .usingJobData(jobDataMap)
                    .build();

            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getTaskCron());
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(cronScheduleBuilder).forJob(jobDetail).build();
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("finish load Task to [Quartz], taskName is [{}]",taskName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SchedulerException e) {
            e.printStackTrace();
            log.error(" load Task to [Quartz] has Exception , taskName is [{}], errer message is [{}]",taskName,e.getMessage());
        }
    }

    public Boolean update(TaskCase taskCase) {
        String taskName =  taskCase.getTaskName();

        Boolean isExist = this.isExist(taskName);
        if(isExist){
            //delete task
            this.delete(taskName);
            concurrentMap.remove(taskName);

            //add new task
            this.add(taskCase);
        }
        return isExist;
    }

    public void delete(String taskName) {
        try {
            TaskCase task = concurrentMap.get(taskName);
            JobKey jobKey = new JobKey(taskName, task.getTaskGroup());
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);

            concurrentMap.remove(taskName);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void resume(String taskName) {
        try {
            TaskCase task = concurrentMap.get(taskName);
            JobKey jobKey = new JobKey(taskName, task.getTaskGroup());
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    public TaskCase loadOne(String taskName){
        if(concurrentMap.containsKey(taskName)){
            return concurrentMap.get(taskName);
        }else{
            return null;
        }
    }

    public List<TaskCase> loadAll(){
        List<TaskCase> taskCaseList = new ArrayList(concurrentMap.entrySet());
        return taskCaseList;
    }
}
