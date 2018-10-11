package com.zhengtoon.framework.socket;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "socket.server")
public class SocketProperties {
    private String host;
    private Integer port;
    private int thread = 10;
    /**
     * 超时时间
     */
    private Integer connectTimeOut = 3000;
}
