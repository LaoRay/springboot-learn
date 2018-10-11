package zhengtoon.framework.task.core.dataresponse;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 18:17
 * @Description:
 */
public class BaseResponseDTO {
    /**
     * Http访问返回码
     */
    protected Integer httpStatus;

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
