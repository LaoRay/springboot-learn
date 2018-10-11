package zhengtoon.framework.task.starter.exceptions;

import org.apache.http.HttpStatus;
import zhengtoon.framework.task.starter.dataresponse.ServerHttpResponse;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 18:11
 * @Description:
 */
public class ServerAliveException extends Exception{
    public static String ERROR_CODE="020001";

    public static String ALIVE_CODE_MSG="no server alive!";

    public ServerAliveException(){
        super(ALIVE_CODE_MSG);
    }

    /**
     * ServerAliveException触发时返回实体
     * @return
     */
    public static ServerHttpResponse getServerAliveExceptionResponse(){
        ServerHttpResponse serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_NOT_FOUND);
        serverHttpResponse.setBizCode(ERROR_CODE);
        serverHttpResponse.setBizDescribe(ALIVE_CODE_MSG);
        return serverHttpResponse;
    }
}
