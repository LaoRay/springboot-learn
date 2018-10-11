package com.zhengtoon.framework.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: qindaorong
 * @Date: 2018/9/21 15:19
 * @Description:
 */
@Data
public abstract class RequestProcessor<T extends BaseCrawlBean> implements Serializable {

    private static final long serialVersionUID = 8150370212814274216L;

    /**
     * 页面中获得需要爬取详情页URL
     * @param rootUrl
     * @return
     *         key      :    详情页面地址URL
     *         value    :    页面分类
     */
    public abstract Map<String,String> loadLists(String rootUrl);

    /**
     * 详情页面处理，并返回对应实体
     * @param url
     * @param type
     * @return
     */
    public abstract T doProcessor(String url,String type);
}
