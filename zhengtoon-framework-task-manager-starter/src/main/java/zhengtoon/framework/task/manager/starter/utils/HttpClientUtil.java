package zhengtoon.framework.task.manager.starter.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 17:49
 * @Description:
 */
public class HttpClientUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    public static String ENCODING = "UTF-8";

    private static String CONTENT_TYPE = "application/json";

    /**
     * 发送POST请求，参数是JSON
     */
    public static HttpResponse requestPost(String url, String jsonParam,Map<String, String> headers){
        HttpResponse resp = null;
        HttpClient client = null;
        try {
            //创建HttpClient对象
            client = HttpClients.createDefault();
            //创建HttpPost对象
            HttpPost httpPost = new HttpPost(url);

            if (headers != null) {
                Set<String> keys = headers.keySet();
                for (Iterator<String> i = keys.iterator(); i.hasNext();) {
                    String key = (String) i.next();
                    httpPost.addHeader(key, headers.get(key));
                }
            }

            //配置请求参数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.DEFAULT)
                    .setExpectContinueEnabled(true)
                    .setSocketTimeout(5000)
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .build();

            httpPost.setConfig(requestConfig);

            String respContent = null;

            //设置参数和请求方式
            StringEntity entity = new StringEntity(jsonParam,ENCODING);//解决中文乱码问题
            entity.setContentEncoding(ENCODING);
            entity.setContentType(CONTENT_TYPE);

            httpPost.setEntity(entity);

            //执行请求
            resp = client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(client != null){
                try {
                    ((CloseableHttpClient) client).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //返回数据
        return resp;
    }


    public static HttpResponse get(String url, Map<String, Object> params){
        return sendGetRequest(url, params,null, "UTF-8");
    }


    /**
     * Http Get请求 请求地址
     *
     * @param url    Get参数
     * @param params 编码
     * @param encode 返回请求结果
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResponse sendGetRequest(String url, Map<String, Object> params,Map<String, String> headers,
                                              String encode) {
        HttpResponse response = null;
        HttpGet httpGet = null;
        HttpClient httpClient = null;
        String result = null;
        try {
            if (null == params) {
                httpGet = new HttpGet(url);
            } else {
                httpGet = new HttpGet(url + dealGetParams(params, encode));
            }

            httpClient = HttpClients.createDefault();

            if (headers != null) {
                Set<String> keys = headers.keySet();
                for (Iterator<String> i = keys.iterator(); i.hasNext();) {
                    String key = (String) i.next();
                    httpGet.addHeader(key, headers.get(key));
                }
            }
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();

        }finally {
            if(httpGet !=  null){
                httpGet.releaseConnection();
            }
            if(httpClient!= null ){
                try {
                    ((CloseableHttpClient) httpClient).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    /**
     * 处理Get方式请求的URL
     *
     * @param params
     * @param enc
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String dealGetParams(Map<String, Object> params, String enc)
            throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append("?");
        for (Entry<String, Object> entry : params.entrySet()) {
            // 参数名=参数&参数名=参数
            sb.append(entry.getKey()).append("=")
                    .append(URLEncoder.encode(String.valueOf(entry.getValue()), enc))
                    .append("&");
        }
        // 删除最后一个&
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 处理POST请求URL
     *
     * @param params
     * @param enc
     * @return
     */
    private static String dealPostParams(Map<String, String> params, String enc) {
        StringBuffer sb = new StringBuffer();
        for (Entry<String, String> entry : params.entrySet()) {
            try {
                sb.append(entry.getKey()).append("=")
                        .append(URLEncoder.encode(entry.getValue(), enc))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String sendGetRequest(String url){
        HttpGet httpGet;
        HttpClient httpClient = null;
        String result = null;
        httpGet = null;
        try {
            httpClient = HttpClients.createDefault();
            httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            if(Objects.nonNull(response) && Objects.nonNull(response.getStatusLine())){
                if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
                    result = EntityUtils.toString(response.getEntity());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(Objects.nonNull(httpGet)){
                httpGet.releaseConnection();
            }
            if(httpClient!= null ){
                try {
                    ((CloseableHttpClient) httpClient).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
