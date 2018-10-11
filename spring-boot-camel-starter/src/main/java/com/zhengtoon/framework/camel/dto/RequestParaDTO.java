package com.zhengtoon.framework.camel.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by lisanchuan on 2018/7/09.
 *
 */
public class RequestParaDTO implements Serializable {

    private static final long serialVersionUID = -5508160301254928863L;
    /**
     * fromUrl
     */
    private String fromUrl;
    /**
     * fromMethod
     */
    private String fromMethod;

    /**
     * para map
     */
    private Map<String,String> paraMap;

    /**
     * request paraMapString
     */
    private String paraMapString;


    public String getParaMapString() {
        return paraMapString;
    }

    public void setParaMapString(String paraMapString) {
        this.paraMapString = paraMapString;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }

    public String getFromMethod() {
        return fromMethod;
    }

    public void setFromMethod(String fromMethod) {
        this.fromMethod = fromMethod;
    }

    public Map<String, String> getParaMap() {
        return paraMap;
    }

    public void setParaMap(Map<String, String> paraMap) {
        this.paraMap = paraMap;
    }

}
