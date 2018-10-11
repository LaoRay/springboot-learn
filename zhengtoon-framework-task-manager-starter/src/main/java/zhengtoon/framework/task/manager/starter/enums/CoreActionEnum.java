package zhengtoon.framework.task.manager.starter.enums;

/**
 * @auther: qindaorong
 * @Date: 2018/7/13 09:47
 * @Description:
 */
public enum CoreActionEnum {

    add("/add"),
    update("/update"),
    delete("/delete"),
    find("/find"),
    queryTasks("/queryTasks")
    ;

    private String actionUrl;

    CoreActionEnum(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getActionUrl() {
        return actionUrl;
    }
}
