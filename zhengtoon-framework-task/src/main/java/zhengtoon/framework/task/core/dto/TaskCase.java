package zhengtoon.framework.task.core.dto;

import java.io.Serializable;

/**
 * @auther: qindaorong
 * @Date: 2018/7/16 11:19
 * @Description:
 */
public class TaskCase  implements Serializable {

    private static final long serialVersionUID = 4991068728607971738L;

    private String taskName;

    private String taskCron;

    private String executeUrl;

    private String taskGroup;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

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
}
