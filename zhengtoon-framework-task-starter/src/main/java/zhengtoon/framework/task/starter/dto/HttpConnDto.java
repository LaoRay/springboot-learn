package zhengtoon.framework.task.starter.dto;

import org.springframework.beans.factory.annotation.Value;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 19:30
 * @Description:
 */
public class HttpConnDto {

    private String taskManageAddress;

    private String taskManageUserName;

    private String taskManagePassword;


    public HttpConnDto(String taskManageAddress, String taskManageUserName, String taskManagePassword) {
        this.taskManageAddress = taskManageAddress;
        this.taskManageUserName = taskManageUserName;
        this.taskManagePassword = taskManagePassword;
    }


    public String getTaskManageAddress() {
        return taskManageAddress;
    }

    public void setTaskManageAddress(String taskManageAddress) {
        this.taskManageAddress = taskManageAddress;
    }

    public String getTaskManageUserName() {
        return taskManageUserName;
    }

    public void setTaskManageUserName(String taskManageUserName) {
        this.taskManageUserName = taskManageUserName;
    }

    public String getTaskManagePassword() {
        return taskManagePassword;
    }

    public void setTaskManagePassword(String taskManagePassword) {
        this.taskManagePassword = taskManagePassword;
    }
}
