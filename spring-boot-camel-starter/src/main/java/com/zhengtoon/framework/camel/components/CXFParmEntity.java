package com.zhengtoon.framework.camel.components;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by lisanchuan on 2018/6/28.
 */
@Component("cxfParmEntity")
@ConfigurationProperties(prefix="cxfserver")
public class CXFParmEntity {
    private String serviceClass;
    private String wsdlLocation;

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getWsdlLocation() {
        return wsdlLocation;
    }

    public void setWsdlLocation(String wsdlLocation) {
        this.wsdlLocation = wsdlLocation;
    }
}
