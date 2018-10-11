package zhengtoon.framework.task.manager.starter.worker;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import zhengtoon.framework.task.manager.starter.dto.HttpConnDto;
import zhengtoon.framework.task.manager.starter.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 19:52
 * @Description:
 */
@Slf4j
public class Worker implements Runnable {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String TRANSPORT_PROTOCOL="http://";
    private static final String HEALTH_CHECK_ACTION ="/healthCheck";
    private static final Integer SLEEP_SECOND = 2;

    private String serverInfo;

    public Worker(HttpConnDto httpConnDto){
        serverInfo = httpConnDto.getTaskManageAddress();
    }

    @Override
    public void run(){
        Boolean isLoop= Boolean.TRUE;
        Map<String, Object> params = new HashMap<String, Object>(0);

        String returnValue;
        HttpResponse response;
        String healthCheckUrl = this.buildHealthCheckUrl();

        while(isLoop){
            try {
                response = HttpClientUtil.get(healthCheckUrl,params);
                if(response !=null ){
                    HttpEntity responseObj = response.getEntity();
                    returnValue = EntityUtils.toString(responseObj,DEFAULT_CHARSET);

                    log.debug("healthCheck request is ok! response value is [{}]",returnValue);
                }else{
                    log.warn("bad request of healthCheck! Task manage maybe not alive!");
                }
            } catch (Exception e) {
                log.debug("healthCheck has Exception, message is [{}]",e.getMessage());
            }

            try {
                TimeUnit.SECONDS.sleep(SLEEP_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String buildHealthCheckUrl(){
        StringBuffer sb = new StringBuffer();
        sb.append(TRANSPORT_PROTOCOL).append(serverInfo).append(HEALTH_CHECK_ACTION);
        return sb.toString();
    }
}
