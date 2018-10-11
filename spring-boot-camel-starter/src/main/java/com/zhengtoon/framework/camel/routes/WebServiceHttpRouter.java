package com.zhengtoon.framework.camel.routes;

import com.zhengtoon.framework.camel.dto.HttpRequestParaDTO;
import com.zhengtoon.framework.camel.processors.FrameworkHttpProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: qindaorong
 * @Date: 2018/6/20 15:56
 * @Description:
 * modify by lisanchuan 2018/7/2 ( delete FrameworkRoute )
 */
public class WebServiceHttpRouter extends RouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(WebServiceHttpRouter.class);

    private String routeId;

    private HttpRequestParaDTO httpRequestParaDTO;

    final String STRING_JETTY = "jetty:";

    final String STRING_URL_END = "?bridgeEndpoint=true&connectionClose=true";


    public WebServiceHttpRouter( String routeId, HttpRequestParaDTO httpRequestParaDTO){
        this.routeId = routeId;
        this.httpRequestParaDTO = httpRequestParaDTO;
    }

    @Override
    public void configure() throws Exception {
        if(!StringUtils.isEmpty(httpRequestParaDTO.getFromUrl())){
            if(HttpMethod.POST.name().equals(httpRequestParaDTO.getFromMethod().toUpperCase())){
                RouteDefinition rd = from("timer://myTimer?repeatCount=1").routeId(routeId);
//                rd.process(new Processor(){
//                    @Override
//                    public void process(Exchange exchange) throws Exception {
//                        Message message= exchange.getIn();
//                        message.setHeader(Exchange.HTTP_METHOD,HttpMethod.POST.name());
//                        buildHttpHeaderAndBody(message,httpRequestParaDTO);
//                    }
//                });

                buildHttpHeaderAndBody(rd,httpRequestParaDTO);
                rd.to(convertHttpUrl(httpRequestParaDTO.getFromUrl(),httpRequestParaDTO.getParaMapString()))
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

    private void buildHttpHeaderAndBody(RouteDefinition rd, HttpRequestParaDTO httpRequestParaDTO) {
        Map<String,String> headerMap = httpRequestParaDTO.getHeaderMap();
        if (!CollectionUtils.isEmpty(headerMap)) {
            Iterator<Map.Entry<String, String>> it = headerMap.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                rd.setHeader(entry.getKey(),constant(entry.getValue()));
            }
        }
        rd.setHeader(Exchange.HTTP_METHOD,constant(HttpMethod.POST.name()));

        //.setBody(constant("parameter1=a&parameter2=b"))
        if(!StringUtils.isEmpty(httpRequestParaDTO.getBody())){
            rd.setBody(constant(httpRequestParaDTO.getBody()));
        }
    }
}
