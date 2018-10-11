package zhengtoon.framework.task.starter.service;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;
import zhengtoon.framework.task.starter.dataresponse.ServerHttpResponse;
import zhengtoon.framework.task.starter.exceptions.ServerAliveException;
import zhengtoon.framework.task.starter.exceptions.TaskHttpException;
import zhengtoon.framework.task.starter.utils.HttpClientUtil;

import java.io.IOException;
import java.util.Map;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 19:20
 * @Description:
 */
public abstract class BaseTaskHttpService {

    private static final String TRANSPORT_PROTOCOL="http://";

    public static final String HTTP_METHODS_GET ="GET";
    public static final String HTTP_METHODS_POST ="POST";

    public static final String TASK_NAME = "taskName";
    public static final String TASK_CODE = "taskCode";
    public static final String HEAD_CONTENT_TYPE = "Content-Type";
    public static final String HEAD_CONTENT_TYPE_VALUE = "application/json";

    /**
     * 处理发送请求Rest请求
     * @param requestMapping    方法映射
     * @param params            参数集合
     * @param methods           处理方法
     * @return
     */
    public ServerHttpResponse sendHttpRequest(
            String serverUrl,
            String requestMapping,
            Map<String ,Object> params,
            String methods
    ) throws ServerAliveException, TaskHttpException {
        ServerHttpResponse serverHttpResponse = null;
        String url = this.buildRequestUrl(serverUrl,requestMapping);

        if(!StringUtils.isEmpty(serverUrl)){
            HttpResponse httpResponse = null;
            if(HTTP_METHODS_GET.equals(methods)){
                httpResponse = HttpClientUtil.get(url,params);
            }
            if(HTTP_METHODS_POST.equals(methods)) {
                JSONObject json = JSONObject.fromObject(params);
                httpResponse = HttpClientUtil.requestPost(url,json.toString(),null);
            }
            serverHttpResponse = this. httpResponseBack(httpResponse);
        }else{
            throw new ServerAliveException();
        }
        return serverHttpResponse;
    }

    /**
     * 处理发送请求Rest请求
     * @param serverPrefix    url
     * @param requestMapping    方法映射
     * @param params            参数集合
     * @param headerParams      head参数
     * @param methods           处理方法
     * @return
     * @throws ServerAliveException
     * @throws TaskHttpException
     */
    public ServerHttpResponse sendHttpRequest(
            String serverPrefix,
            String requestMapping,
            Map<String ,Object> params,
            Map<String ,String> headerParams,
            String methods
    ) throws ServerAliveException, TaskHttpException {
        ServerHttpResponse serverHttpResponse = null;
        String url =  this.buildRequestUrl(serverPrefix,requestMapping);

        if(!StringUtils.isEmpty(serverPrefix)){
            HttpResponse httpResponse = null;
            if(HTTP_METHODS_GET.equals(methods)){
                if(headerParams != null ){
                    httpResponse = HttpClientUtil.sendGetRequest(url,params,headerParams,"UTF-8");
                }else{
                    httpResponse = HttpClientUtil.get(url,params);
                }
            }
            if(HTTP_METHODS_POST.equals(methods)) {
                JSONObject json = JSONObject.fromObject(params);
                if(headerParams != null ){
                    httpResponse = HttpClientUtil.requestPost(url,json.toString(),headerParams);
                }else{
                    httpResponse = HttpClientUtil.requestPost(url,json.toString(),null);
                }
            }

            serverHttpResponse = this. httpResponseBack(httpResponse);

        }else{
            throw new ServerAliveException();
        }
        return serverHttpResponse;
    }

    private ServerHttpResponse httpResponseBack(HttpResponse httpResponse) throws TaskHttpException
    {
        //HTTP级别返回状态码
        int httpCode = httpResponse.getStatusLine().getStatusCode();

        ServerHttpResponse serverHttpResponse = null;

        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String resultStr = null;
            try {
                resultStr = EntityUtils.toString(httpResponse.getEntity());
            }catch (IOException e){
                throw  new TaskHttpException();
            }

            JSONObject jsonObject = JSONObject.fromObject(resultStr);
            serverHttpResponse = (ServerHttpResponse)JSONObject.toBean(jsonObject,ServerHttpResponse.class);
        }else{
            serverHttpResponse  = new ServerHttpResponse(httpCode);
        }
        return serverHttpResponse;
    }

    private String buildRequestUrl(String serverInfo, String actionUrl){
        StringBuffer sb = new StringBuffer();
        sb.append(TRANSPORT_PROTOCOL).append(serverInfo).append(actionUrl);
        return sb.toString();
    }
}
