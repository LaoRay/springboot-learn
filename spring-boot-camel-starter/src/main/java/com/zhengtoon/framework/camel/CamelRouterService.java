package com.zhengtoon.framework.camel;

import com.alibaba.fastjson.JSONObject;
import com.zhengtoon.framework.camel.dto.HttpRequestParaDTO;
import com.zhengtoon.framework.camel.dto.MessageDTO;
import com.zhengtoon.framework.camel.dto.WebserviceRequestParaDTO;
import com.zhengtoon.framework.camel.routes.CXFRouter;
import com.zhengtoon.framework.camel.routes.HttpRouter;
import com.zhengtoon.framework.camel.routes.WebServiceHttpRouter;
import com.zhengtoon.framework.camel.workers.LoadWorker;
import org.apache.camel.builder.NoErrorHandlerBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @auther: qindaorong
 * @Date: 2018/6/27 09:29
 * @Description:
 * modify by lisanchuan 2018/7/2 ( delete FrameworkRoute and change function of sendHttpRequest 、 sendWebserviceRequest )
 */
public class CamelRouterService {

    private static final Logger logger = LoggerFactory.getLogger(CamelRouterService.class);

    private ModelCamelContext camelContext;

    public static Map<String, MessageDTO> map = new ConcurrentHashMap<String, MessageDTO>();


    public CamelRouterService() {

    }

    public void init() {
        try {
            //init ModelCamelContext
            initCamelContext();
        } catch (Exception e) {
            logger.error("Load route is failed , Exception Message is {}", e.getMessage());
        } finally {
            logger.info("[CamelRouterService] has bean init!");
        }
    }

    public void initCamelContext() {
        camelContext = new DefaultCamelContext();
        try {
            logger.info("[camelContext] start to init");
            camelContext.setErrorHandlerBuilder(new NoErrorHandlerBuilder());
            camelContext.start();
            logger.info("[camelContext] start completed....");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[camelContext] has Exception during init , message is {}", e.getMessage());
        }
        logger.info("[camelContext] end to init .....");
    }

    public ModelCamelContext getCamelContext() {
        return camelContext;
    }


    private String construct2ParaString(Map<String, String> paraMap) {
        StringBuffer sb = new StringBuffer();
        String paraString = "";
        if (!CollectionUtils.isEmpty(paraMap)) {
            Iterator<Map.Entry<String, String>> it = paraMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                sb.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }

            paraString = sb.toString();
            if (paraString.endsWith("&")) {
                paraString = paraString.substring(0, paraString.length() - 1);
            }
        }
        return paraString;
    }

    /**
     * sendHttpRequest
     * @param httpRequestParaDTO
     * @return
     */
    public MessageDTO sendHttpRequest( HttpRequestParaDTO httpRequestParaDTO) {
            String uuid = UUID.randomUUID().toString();
            MessageDTO messageDTO = new MessageDTO();
            Map<String, String> paraMap = new HashMap<String, String>();
            try {

                if (StringUtils.isEmpty(httpRequestParaDTO.getBody())) {
                    paraMap = httpRequestParaDTO.getParaMap();
                    String paraString = construct2ParaString(paraMap);
                    httpRequestParaDTO.setParaMapString(paraString);
                    camelContext.addRoutes(new HttpRouter(httpRequestParaDTO, uuid));
                } else {
                    paraMap = httpRequestParaDTO.getParaMap();
                    String paraString = construct2ParaString(paraMap);
                    httpRequestParaDTO.setParaMapString(paraString);
                    camelContext.addRoutes(new WebServiceHttpRouter( uuid, httpRequestParaDTO));
                }
                map.put(uuid, messageDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //start one thread
            messageDTO = this.loadMessageDTO(uuid);
            return messageDTO;
    }

    /**
     * sendWebserviceRequest
     * @param webserviceRequestParaDTO
     * @return
     */
    public MessageDTO sendWebserviceRequest(WebserviceRequestParaDTO webserviceRequestParaDTO) {
            webserviceRequestParaDTO.setParaMapString(JSONObject.toJSONString(webserviceRequestParaDTO.getParaMap()));
            String uuid = UUID.randomUUID().toString();

            MessageDTO messageDTO = new MessageDTO();
            try {
                camelContext.addRoutes(new CXFRouter(webserviceRequestParaDTO, uuid));
                map.put(uuid, messageDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            messageDTO = this.loadMessageDTO(uuid);

            return messageDTO;
    }

    private MessageDTO loadMessageDTO (String routeId){

        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        LoadWorker loadWorker = new LoadWorker(routeId);
        FutureTask<MessageDTO> futureTask = new FutureTask<MessageDTO>(loadWorker);
        threadPool.execute(futureTask);

        MessageDTO messageDTO= null ;
        try {
            messageDTO = futureTask.get(30000L, TimeUnit.MILLISECONDS);
            camelContext.removeRoute(routeId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }

        return messageDTO;
    }

    public void setMessageDTOByRouteId(String routeId, MessageDTO messageDTO) {
        if (map.containsKey(routeId)) {
            map.put(routeId, messageDTO);
        }
    }
}
