package com.zhengtoon.framework.camel.routes;

import com.zhengtoon.framework.camel.dto.HttpRequestParaDTO;
import com.zhengtoon.framework.camel.processors.FrameworkHttpProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

/**
 * @Auther: qindaorong
 * @Date: 2018/6/20 15:56
 * @Description:
 * modify by lisanchuan 2018/7/2 ( change FrameworkRoute to HttpRequestParaDTO)
 */
public class HttpRouter extends RouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(HttpRouter.class);

    private HttpRequestParaDTO httpRequestParaDTO;

    private String routeId;

    final String STRING_JETTY = "jetty:";

    final String STRING_URL_END = "?bridgeEndpoint=true&connectionClose=true";

    public HttpRouter(HttpRequestParaDTO httpRequestParaDTO, String routeId){
        this.httpRequestParaDTO = httpRequestParaDTO;
        this.routeId = routeId;
    }

    @Override
    public void configure() throws Exception {
        if(!StringUtils.isEmpty(httpRequestParaDTO.getFromUrl())){
            if(HttpMethod.POST.name().equals(httpRequestParaDTO.getFromMethod().toUpperCase())){
                from("timer://myTimer?repeatCount=1").routeId(routeId)
                        //.to("jetty:http://localhost:8080/hello?bridgeEndpoint=true&connectionClose=true")
                        .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST.name()))
                        //.setBody(constant("parameter1=a&parameter2=b"))
                        .to(convertHttpUrl(httpRequestParaDTO.getFromUrl(),httpRequestParaDTO.getParaMapString()))
                        .process(new FrameworkHttpProcessor(httpRequestParaDTO))
                        .end();
            }else{
                from("timer://myTimer?repeatCount=1").routeId(routeId)
                        //.to("jetty:http://localhost:8080/hello?bridgeEndpoint=true&connectionClose=true")
                        .to(convertHttpUrl(httpRequestParaDTO.getFromUrl(),httpRequestParaDTO.getParaMapString()))
                        .process(new FrameworkHttpProcessor(httpRequestParaDTO))
                        .end();
            }
        }else{
            logger.error("watch url is null, please check url in [application.yml]");
        }
    }

    private String convertHttpUrl(String url,String parameterUrl ){
        StringBuffer sb = new StringBuffer();
        if(StringUtils.isEmpty(parameterUrl)){
            sb.append(STRING_JETTY).append(url).append(STRING_URL_END);
        }else{
            sb.append(STRING_JETTY).append(url).append("?").append(parameterUrl).append(STRING_URL_END);
        }
        return sb.toString();
    }
}
