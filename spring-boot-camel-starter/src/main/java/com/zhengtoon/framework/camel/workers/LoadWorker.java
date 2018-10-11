package com.zhengtoon.framework.camel.workers;

import com.zhengtoon.framework.camel.CamelRouterService;
import com.zhengtoon.framework.camel.dto.MessageDTO;
import org.springframework.util.StringUtils;

import java.util.concurrent.Callable;

/**
 * @auther: qindaorong
 * @Date: 2018/6/26 15:21
 * @Description:
 */
public class LoadWorker implements Callable<MessageDTO> {

    private String routeId;

    public LoadWorker(String routeId){
        this.routeId = routeId;
    }

    @Override
    public MessageDTO call() throws Exception {
        while (true) {
            if (!StringUtils.isEmpty(CamelRouterService.map.get(routeId).getResponseBody())) {
                MessageDTO messageDTO = CamelRouterService.map.get(routeId);
                    return messageDTO;
            }
        }
    }
}
