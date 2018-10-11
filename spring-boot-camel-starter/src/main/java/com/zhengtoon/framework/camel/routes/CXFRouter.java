package com.zhengtoon.framework.camel.routes;


import com.zhengtoon.framework.camel.components.CXFParmEntity;
import com.zhengtoon.framework.camel.components.SpringUtil;
import com.zhengtoon.framework.camel.dto.WebserviceRequestParaDTO;
import com.zhengtoon.framework.camel.processors.CXFInParamProcessor;
import com.zhengtoon.framework.camel.processors.CXFProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lisanchuan on 2018/6/26.
 *
 * modify by lisanchuan 2018/7/2 ( change FrameworkRoute to WebserviceRequestParaDTO)
 */
public class CXFRouter extends RouteBuilder {
    private final Logger logger = LoggerFactory.getLogger(CXFRouter.class);

    CXFParmEntity cxfParmEntity = (CXFParmEntity) SpringUtil.getBean("cxfParmEntity");
    private WebserviceRequestParaDTO webserviceRequestParaDTO;
    private String routeId;

    public CXFRouter(WebserviceRequestParaDTO webserviceRequestParaDTO, String routeId) {
        this.webserviceRequestParaDTO = webserviceRequestParaDTO;
        this.routeId = routeId;
    }

    @Override
    public void configure() throws Exception {
        logger.info("CXFRountUrL is " + webserviceRequestParaDTO.getFromUrl() + "?wsdl");

        StringBuffer router_endpoint = new StringBuffer("cxf:")
                .append(webserviceRequestParaDTO.getFromUrl())
                .append("?")
                .append(cxfParmEntity.getServiceClass())
                .append("&")
                .append(cxfParmEntity.getWsdlLocation());

        from("timer://myTimer?repeatCount=1")
                //from(router_endpoint.toString())
                .routeId(routeId)
                .process(new CXFInParamProcessor(webserviceRequestParaDTO))
                .to(router_endpoint.toString())
                .process(new CXFProcessor(webserviceRequestParaDTO))
                // .to("log:CamelCxfExample?showExchangeId=true").to(serviceEndpoint)
                .end();
    }
}
