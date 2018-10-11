package com.zhengtoon.framework.camel.dto;

import java.io.Serializable;

/**
 * @auther: qindaorong
 * @Date: 2018/6/22 17:07
 * @Description:
 * @modify by lisanchuan 2018/7/2   (delete topic )
 */
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 4309991981688309458L;

    public MessageDTO() {

    }

    public MessageDTO(String from, String responseBody) {

        this.from = from;
        this.responseBody = responseBody;
    }

    private String from;


    private String responseBody;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
