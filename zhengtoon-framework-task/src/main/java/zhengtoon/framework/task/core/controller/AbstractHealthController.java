package zhengtoon.framework.task.core.controller;

/**
 * @auther: qindaorong
 * @Date: 2018/7/13 16:03
 * @Description:
 */
public abstract class AbstractHealthController {
    /**
     * 健康检查通过，返回“0”
     */
    public static String HEALTH_CHECK_OK = "0";

    /**
     * 健康检查抽象类
     * @return
     */
    public abstract String healthCheck();
}
