package zhengtoon.framework.task.starter.enums;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 18:21
 * @Description:
 */
public enum HttpRequestEnum {

    REQUEST_SUCCESS(010001,"request success"),
    REQUEST_FAIL(010002,"request fail");

    private int code;

    private String message;

    HttpRequestEnum(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessageByCode(int code) {
        String message = null;
        for (HttpRequestEnum httpRequestEnum : HttpRequestEnum.values()) {
            if (httpRequestEnum.code == code) {
                message = httpRequestEnum.message;
                break;
            }
        }
        return message;
    }
}
