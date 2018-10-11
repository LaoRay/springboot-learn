package com.zhengtoon.framework.email.dto;

import lombok.Data;
import org.springframework.core.io.FileSystemResource;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther: qindaorong
 * @Date: 2018/7/31 16:08
 * @Description:
 */
@Data
public class EmailDTO implements Serializable {
    //必填参数
    private List<String> toList;//接收方集合
    private String subject;//主题
    private String content;//邮件内容

    //选填
    private List<String> ccList;//抄送方集合
    private List<String> bccList;//暗送方集合
    private String template;//模板
    private HashMap<String, String> kvMap;// 自定义参数
    private Map<String,FileSystemResource> fileMap;//附件集合
}
