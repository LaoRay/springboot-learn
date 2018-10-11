package zhengtoon.framework.task.manager.starter.dto;

import lombok.Data;

@Data
public class TaskDTO {

    private String id;
    /**
     * 执行时间Cron表达式
     */
    private String taskCron;

    /**
     * 回调执行路径
     */
    private String executeUrl;

    /**
     * 定时任务分组
     */
    private String taskGroup;

    /**
     * 定时任务名称
     */
    private String taskName;


}
