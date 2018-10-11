package zhengtoon.framework.task.manager.starter.components;

import com.zhengtoon.framework.lock.annotions.RedisLock;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhengtoon.framework.task.manager.starter.dto.TaskDTO;
import zhengtoon.framework.task.manager.starter.enums.JobContextKeyEnum;
import zhengtoon.framework.task.manager.starter.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther: qindaorong
 * @Date: 2018/7/16 11:26
 * @Description:
 */
public class JobTask implements Job{

    private static final Logger logger = LoggerFactory.getLogger(JobTask.class);

    @Override
    @RedisLock
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.info("[JobTask] has bean execute!");

        TaskDTO taskCase = (TaskDTO)jobExecutionContext.getJobDetail().getJobDataMap().get(JobContextKeyEnum.JOB_CONTEXT_KEY.getKey());
        String url =taskCase.getExecuteUrl();

        //send http request
        Map<String, Object> params = new HashMap<String, Object>(0);
        Map<String, String> headers = new HashMap<String, String>();
        HttpResponse httpResponse = HttpClientUtil.sendGetRequest(url,params,headers,HttpClientUtil.ENCODING);

        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            logger.info("[JobTask] execute over!");
        }else{
            logger.error("[JobTask] has bean execute,httpStatusCode is [{}]",httpResponse.getStatusLine().getStatusCode());
            JobDetail jobDetail= jobExecutionContext.getJobDetail();
            String jobName = jobDetail.getKey().getName();

            StringBuffer sb = new StringBuffer();
            sb.append("[").
                    append(jobName)
                    .append("] ")
                    .append(" has Exception, please check it!");

            throw new JobExecutionException(sb.toString());
        }
    }
}
