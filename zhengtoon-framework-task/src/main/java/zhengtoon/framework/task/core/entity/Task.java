package zhengtoon.framework.task.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yanghu
 * @since 2018-07-17
 */
@TableName("wiz_task")
public class Task extends Model<Task> {

    private static final long serialVersionUID = 1L;

    private Long id;
    @TableField("task_name")
    private String taskName;
    @TableField("task_cron")
    private String taskCron;
    @TableField("execute_url")
    private String executeUrl;
    @TableField("task_group")
    private String taskGroup;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Task{" +
        ", id=" + id +
        ", taskName=" + taskName +
        ", taskCron=" + taskCron +
        ", executeUrl=" + executeUrl +
        ", taskGroup=" + taskGroup +
        "}";
    }
}
