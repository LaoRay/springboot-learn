package com.zhengtoon.framework.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: qindaorong
 * @Date: 2018/9/21 09:53
 * @Description:
 */
@Data
public class Site implements Serializable {

    private static final long serialVersionUID = -6454232269889843995L;

    /**
     * 执行爬虫对象集合
     * key：      每个分类集合根目录【列表页面url】
     * value：   每个对象处理集合
     */
    private Map<String, RequestProcessor> processorMap;
}
