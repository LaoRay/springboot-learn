package zhengtoon.framework.task.starter.dto;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 17:20
 * @Description:
 */
public class TaskDTO {

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

    public String getTaskCron() {
        return taskCron;
    }

    public void setTaskCron(String taskCron) {
        this.taskCron = taskCron;
    }

    public String getExecuteUrl() {
        return executeUrl;
    }

    public void setExecuteUrl(String executeUrl) {
        this.executeUrl = executeUrl;
    }

    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
