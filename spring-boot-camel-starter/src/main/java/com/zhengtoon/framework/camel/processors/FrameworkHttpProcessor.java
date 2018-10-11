package com.zhengtoon.framework.camel.processors;

import com.alibaba.fastjson.JSONObject;
import com.zhengtoon.framework.camel.CamelRouterService;
import com.zhengtoon.framework.camel.components.SpringUtil;
import com.zhengtoon.framework.camel.dto.HttpRequestParaDTO;
import com.zhengtoon.framework.camel.dto.MessageDTO;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.model.ModelCamelContext;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: qindaorong
 * @Date: 2018/6/20 16:12
 * @Description:
 * modify by lisanchuan 2018/7/2 ( change FrameworkRoute to HttpRequestParaDTO)
 */
public class FrameworkHttpProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(FrameworkHttpProcessor.class);

    private HttpRequestParaDTO httpRequestParaDTO;

    public FrameworkHttpProcessor(HttpRequestParaDTO httpRequestParaDTO) {
        this.httpRequestParaDTO = httpRequestParaDTO;
    }

    @Override
    public void process(Exchange exchange){

        CamelRouterService camelRouterService = (CamelRouterService) SpringUtil.getBean("camelRouterService");
        ModelCamelContext camelContext = camelRouterService.getCamelContext();

        String routeId = exchange.getFromRouteId();
        try {
            camelContext.stopRoute(routeId,1L,TimeUnit.SECONDS);

            byte[] btArray = (byte[]) exchange.getIn().getBody();
            InputStream sbs = new ByteArrayInputStream(btArray);
            String inputContext = this.analysisMessage(sbs);
            sbs.close();
            logger.info("camel worked!  response message is original is [{}].", inputContext);

            // 存入到exchange的out区域
            if (exchange.getPattern() == ExchangePattern.InOnly) {
                Message outMessage = exchange.getOut();

                //封装DTO返回
                //JSONObject jsonObject = JSON.parseObject(inputContext);

                String from = this.httpRequestParaDTO.getFromUrl();
               // String topic = this.frameworkRoute.getTopic();

                MessageDTO messageDTO = new MessageDTO(from, inputContext);
                outMessage.setBody(JSONObject.toJSONString(messageDTO));
                logger.info("[convertToMessageDTO] is completion. MessageDTO is [{}]", JSONObject.toJSONString(messageDTO));

                camelRouterService.setMessageDTOByRouteId(routeId, messageDTO);
            }
        } catch (Exception e) {
            logger.info("Exception is [{}].", e);
        }
    }

    /**
     * 从stream中分析字符串内容
     *
     * @param bodyStream
     * @return
     */
    private String analysisMessage(InputStream bodyStream) throws IOException {
        String responseStr = IOUtils.toString(bodyStream, "UTF-8");
        return responseStr;
    }

    private MessageDTO convertToMessageDTO(JSONObject jsonObject) {
        String from = this.httpRequestParaDTO.getFromUrl();
        String responseBody = jsonObject.toJSONString();
        return new MessageDTO(from, responseBody);
    }
}
