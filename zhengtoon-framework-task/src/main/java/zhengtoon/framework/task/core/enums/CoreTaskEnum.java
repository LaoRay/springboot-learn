package zhengtoon.framework.task.core.enums;

import org.apache.http.HttpStatus;
import zhengtoon.framework.task.core.dataresponse.ServerHttpResponse;

/**
 * CoreTaskEnum
 *
 * @author qindaorong
 * @date 2017/10/16
 */
public enum CoreTaskEnum {
    
    /**
     * CoreTaskEnum返回消息成功
     */
    CORE_TASK_SEND_SUC("010001"),
    /**
     * CoreTaskEnum返回消息失败
     */
    CORE_TASK_SEND_FAIL("010002");
    
    private String code;
    
    CoreTaskEnum(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
    
    
    /**
     * ServerAliveException触发时返回实体
     * @return
     */
    public static ServerHttpResponse getHystricResponse(){
        ServerHttpResponse serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_NOT_FOUND);
        serverHttpResponse.setBizCode(HYSTRIC_ERROR_CODE);
        serverHttpResponse.setBizDescribe(HYSTRIC_ERROR_MSG);
        return serverHttpResponse;
    }
    
    
    /**
     * HYSTRIC错误代码
     */
    public static String HYSTRIC_ERROR_CODE = "090001";
    
    /**
     * HYSTRIC错误信息提示
     */
    public static String HYSTRIC_ERROR_MSG = "request has into hystric, server is down!";
}
