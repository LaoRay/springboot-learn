package com.zhengtoon.framework.camel.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @auther: qindaorong
 * @Date: 2018/6/27 16:32
 * @Description:
 * modify by lisanchuan 2018/7/2   ( add fromUrl、fromMethod、 paraMapString and delete routeTopic 、routeGroup )
 */
public class HttpRequestParaDTO  extends  RequestParaDTO  {

    /**
     * head map
     */
    private Map<String,String> headerMap;

    /**
     * request body
     */
    private String body;

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
