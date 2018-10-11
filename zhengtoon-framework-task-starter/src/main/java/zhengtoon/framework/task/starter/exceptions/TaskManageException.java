package zhengtoon.framework.task.starter.exceptions;

import org.apache.http.HttpStatus;
import zhengtoon.framework.task.starter.dataresponse.ServerHttpResponse;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 18:12
 * @Description:
 */
public class TaskManageException extends NullPointerException {
    public static String ERROR_CODE="020003";

    public static String CODE_MSG="Task Manage Exception!";

    public TaskManageException(){
        super(CODE_MSG);
    }

    /**
     * TaskManageException触发时返回实体
     * @return
     */
    public static ServerHttpResponse getTaskManageExceptionResponse(){
        ServerHttpResponse serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        serverHttpResponse.setBizCode(ERROR_CODE);
        serverHttpResponse.setBizDescribe(CODE_MSG);
        return serverHttpResponse;
    }
}
