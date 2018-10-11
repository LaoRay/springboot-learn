package com.zhengtoon.framework.camel.processors;

import com.alibaba.fastjson.JSONObject;
import com.zhengtoon.framework.camel.CamelRouterService;
import com.zhengtoon.framework.camel.components.SpringUtil;
import com.zhengtoon.framework.camel.dto.MessageDTO;
import com.zhengtoon.framework.camel.dto.WebserviceRequestParaDTO;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.model.ModelCamelContext;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by lisanchuan on 2018/6/27.
 * modify by lisanchuan 2018/7/2 ( change FrameworkRoute to WebserviceRequestParaDTO)
 */
public class CXFProcessor implements Processor {
    private final Logger logger = LoggerFactory.getLogger(CXFProcessor.class);
    private WebserviceRequestParaDTO webserviceRequestParaDTO;

    public CXFProcessor(WebserviceRequestParaDTO webserviceRequestParaDTO) {
        this.webserviceRequestParaDTO = webserviceRequestParaDTO;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        CamelRouterService camelRouterService = (CamelRouterService) SpringUtil.getBean("camelRouterService");
        ModelCamelContext camelContext = camelRouterService.getCamelContext();
        String routeId = exchange.getFromRouteId();

        camelContext.stopRoute(routeId,1L,TimeUnit.SECONDS);

        MessageContentsList bodyStream = (MessageContentsList) exchange.getIn().getBody();
        String responseStr = bodyStream.get(0).toString();
        if (exchange.getPattern() == ExchangePattern.InOnly) {
            Message outMessage = exchange.getOut();
            MessageDTO messageDTO = this.convertToMessageDTO(responseStr);
            outMessage.setBody(JSONObject.toJSONString(messageDTO));
            logger.info("[convertToMessageDTO] is completion. MessageDTO is [{}]", JSONObject.toJSONString(messageDTO));
            camelRouterService.setMessageDTOByRouteId(routeId, messageDTO);
        }
}
    private MessageDTO convertToMessageDTO(String responseStr) {
        String from = this.webserviceRequestParaDTO.getFromUrl();
        String responseBody = responseStr;
        return new MessageDTO(from, responseBody);
    }
}
