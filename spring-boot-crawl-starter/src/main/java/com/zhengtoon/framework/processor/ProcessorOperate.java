package com.zhengtoon.framework.processor;

import com.zhengtoon.framework.bean.Site;

import java.util.List;

/**
 * @author: qindaorong
 * @Date: 2018/9/25 09:39
 * @Description:
 */
public interface ProcessorOperate {

    void init(Site site);

    void start();

    Boolean isReady();

    void stop();

    void reset();

    List<?> loadProcessorResultByKey(String rootUrl);
}
