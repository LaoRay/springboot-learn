package com.zhengtoon.framework.camel.processors;


import com.zhengtoon.framework.camel.dto.WebserviceRequestParaDTO;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by lisanchuan on 2018/6/28.
 * modify by lisanchuan 2018/7/2 ( change FrameworkRoute to WebserviceRequestParaDTO)
 */
public class CXFInParamProcessor implements Processor {
    private WebserviceRequestParaDTO webserviceRequestParaDTO;

    public CXFInParamProcessor(WebserviceRequestParaDTO webserviceRequestParaDTO) {
        this.webserviceRequestParaDTO = webserviceRequestParaDTO;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody(webserviceRequestParaDTO.getParaMapString());
    }
}
