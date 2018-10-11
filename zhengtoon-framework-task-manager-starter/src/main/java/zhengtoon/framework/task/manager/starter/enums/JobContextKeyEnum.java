package zhengtoon.framework.task.manager.starter.enums;

/**
 * @auther: qindaorong
 * @Date: 2018/7/16 11:33
 * @Description:
 */
public enum JobContextKeyEnum {
    JOB_CONTEXT_KEY("jobContext");

    private String key;

    JobContextKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
