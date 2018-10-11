package zhengtoon.framework.task.starter.exceptions;

import org.apache.http.HttpStatus;
import zhengtoon.framework.task.starter.dataresponse.ServerHttpResponse;

import java.io.IOException;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 18:12
 * @Description:
 */
public class TaskHttpException extends IOException {
    public static String ERROR_CODE="020002";

    public static String CODE_MSG="Task Http Exception!";

    public TaskHttpException(){
        super(CODE_MSG);
    }

    /**
     * TaskHttpException触发时返回实体
     * @return
     */
    public static ServerHttpResponse getTaskHttpExceptionResponse(){
        ServerHttpResponse serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        serverHttpResponse.setBizCode(ERROR_CODE);
        serverHttpResponse.setBizDescribe(CODE_MSG);
        return serverHttpResponse;
    }
}
