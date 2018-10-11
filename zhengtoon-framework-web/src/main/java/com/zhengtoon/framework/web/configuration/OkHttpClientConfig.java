package com.zhengtoon.framework.web.configuration;

import com.zhengtoon.framework.exception.BusinessException;
import com.zhengtoon.framework.exception.CoreExceptionCodes;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * okHttpclient创建对象集成重试机制
 *
 * @author LuCheng.Qi
 * @since 2018-07-18
 * Company:北京思源政务通有限公司
 */
@Slf4j
@Configuration
public class OkHttpClientConfig {

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public okhttp3.OkHttpClient okHttpClient(final RetryTemplate retry ,final OkHttp3Properties okHttp3Properties) {
        return new OkHttpClient.Builder().readTimeout(okHttp3Properties.getReadTimeout(), TimeUnit.SECONDS)
                .connectTimeout(okHttp3Properties.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(okHttp3Properties.getWriteTimeout(), TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(
                        okHttp3Properties.getMaxIdleConnections(), 60, TimeUnit.SECONDS))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(final Chain chain){
                        final Request request = chain.request();
                        try {
                            return retry.execute(new RetryCallback<Response, IOException>() {
                                @Override
                                public Response doWithRetry(RetryContext context) throws IOException {
                                    Response response = chain.proceed(request);
                                    log.debug("Request method = {} url = {} Response = {}", request.method(), request.url(), response);
                                    return response;
                                }
                            });
                        } catch (Exception e) {
                            log.error(String.format("请求外部接口异常[Request method = %s url = %s]",request.method(),request.url()),e);
                            throw new BusinessException(CoreExceptionCodes.UNKNOWN_ERROR);
                        }
                    }
                }).build();
    }

}
