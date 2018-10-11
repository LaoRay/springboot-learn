package zhengtoon.framework.task.manager.starter.components;

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
import zhengtoon.framework.task.manager.starter.domain.XmlTask;
import zhengtoon.framework.task.manager.starter.domain.XmlTaskList;
import zhengtoon.framework.task.manager.starter.dto.TaskDTO;
import zhengtoon.framework.task.manager.starter.enums.JobContextKeyEnum;
import zhengtoon.framework.task.manager.starter.service.TaskManageService;
import zhengtoon.framework.task.manager.starter.utils.XmlBuilder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther: qindaorong
 * @Date: 2018/7/16 11:16
 * @Description:
 * modify-auther: lisanchuan
 * @Date: 2018/8/21
 */
@Component
@Import(TaskFactory.class)
public class TaskFactory implements ImportBeanDefinitionRegistrar {

    private static final Logger log = LoggerFactory.getLogger(TaskFactory.class);

    private static final String BEAN_NAME = "taskFactory";
    private static final String EXECUTE_CLASS = "zhengtoon.framework.task.manager.starter.components.JobTask";

    @Autowired
    private Scheduler scheduler;

    @Autowired
    @Lazy
    private TaskManageService taskManageServer;

    private static ConcurrentHashMap<String, TaskDTO> concurrentMap = new ConcurrentHashMap<String, TaskDTO>();
    private static XmlTaskList xmlTaskList;

    @PostConstruct
    private void init(){
        log.info("start load DB task to memory!");
        loadTaskXml();
        log.info("finish load DB task to memory!");
    }

    public void initDbTask(){
        List<TaskDTO> taskCaseList = taskManageServer.queryTasks();
        if(!CollectionUtils.isEmpty(taskCaseList)){
            for(TaskDTO taskCase : taskCaseList){
                this.add(taskCase);
            }
        }else{
            log.info("DB is empty! no nead to load!");
        }
        List<XmlTask> taskList = xmlTaskList.getXmlTaskList();
        if(!CollectionUtils.isEmpty(taskList)){
            for(XmlTask xmlTask : taskList){
                if(!concurrentMap.containsKey(xmlTask.getId())){
                    TaskDTO taskDTO =new TaskDTO();
                    taskDTO.setId(xmlTask.getId());
                    taskDTO.setTaskName(xmlTask.getTaskName());
                    taskDTO.setTaskCron(xmlTask.getTaskCron());
                    taskDTO.setExecuteUrl(xmlTask.getExecuteUrl());
                    taskDTO.setTaskGroup(xmlTask.getTaskGroup());
                    this.add(taskDTO);
                }
            }
        }
    }

    public void loadTaskXml(){
        try {
            xmlTaskList = XmlBuilder.loadTasks();

        } catch (Exception e) {
            log.error("Load task is failed , Exception Message is {}", e.getMessage());
        }

    }

    public Boolean isExist(String taskId){
        if(concurrentMap.containsKey(taskId)){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    public Boolean add(TaskDTO task){
        String taskId = task.getId();
        Boolean isNew = Boolean.FALSE;
        if(!isExist(taskId)){
            concurrentMap.put(taskId, task);
            this.startJob(taskId);
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

    public void startJob(String taskId){

        TaskDTO task = concurrentMap.get(taskId);
        try {

            log.info("start load Task to [Quartz], taskId is [{}]",taskId);
            JobKey jobKey = new JobKey(taskId, task.getTaskGroup());
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
            log.info("finish load Task to [Quartz], taskId is [{}]",taskId);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SchedulerException e) {
            e.printStackTrace();
            log.error(" load Task to [Quartz] has Exception , taskId is [{}], errer message is [{}]",taskId,e.getMessage());
        }
    }

    public Boolean update(TaskDTO taskCase) {
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
            TaskDTO task = concurrentMap.get(taskName);
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
            TaskDTO task = concurrentMap.get(taskName);
            JobKey jobKey = new JobKey(taskName, task.getTaskGroup());
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    public TaskDTO loadOne(String taskName){
        if(concurrentMap.containsKey(taskName)){
            return concurrentMap.get(taskName);
        }else{
            return null;
        }
    }

    public List<TaskDTO> loadAll(){
        List<TaskDTO> taskCaseList = new ArrayList(concurrentMap.entrySet());
        return taskCaseList;
    }
}
