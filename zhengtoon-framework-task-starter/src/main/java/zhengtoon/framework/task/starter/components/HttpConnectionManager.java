package zhengtoon.framework.task.starter.components;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 17:51
 * @Description:
 */
@Component
public class HttpConnectionManager {

    private static PoolingHttpClientConnectionManager cm;

    private static HttpRequestRetryHandler httpRequestRetryHandler;

    private static final Integer TRY_CONNECT_TIMES = 5;

    @PostConstruct
    public void init(){
        ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainSocketFactory)
                .build();
        cm = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加到200
        cm.setMaxTotal(200);
        // 将每个路由基础的连接增加到20
        cm.setDefaultMaxPerRoute(20);

        //socket配置
        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)//是否即使发送
                .setSoReuseAddress(true)//是否访问完毕后立刻释放
                .setSoTimeout(500)//接收数据等待时间
                .setSoLinger(60)//关闭socket最长等待时间（单位秒）
                .setSoKeepAlive(true)//开启见识TCP连接
                .build();

        cm.setDefaultSocketConfig(socketConfig);

        //请求重试处理
        httpRequestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception,int executionCount, HttpContext context) {
                // 如果已经重试了5次，就放弃
                if (executionCount >= TRY_CONNECT_TIMES) {
                    return false;
                }
                // 如果服务器丢掉了连接，那么就重试
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                // 超时
                if (exception instanceof InterruptedIOException) {
                    return false;
                }
                // 目标服务器不可达
                if (exception instanceof UnknownHostException) {
                    return false;
                }
                // 连接被拒绝
                if (exception instanceof ConnectTimeoutException) {
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
    }

    public static HttpClient getHttpClient() {
        CloseableHttpClient httpClient =HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(httpRequestRetryHandler)
                .build();
        return httpClient;
    }
}
