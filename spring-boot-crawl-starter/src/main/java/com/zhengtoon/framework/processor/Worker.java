package com.zhengtoon.framework.processor;

import com.zhengtoon.framework.bean.BaseCrawlBean;
import com.zhengtoon.framework.bean.RequestProcessor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * @author: qindaorong
 * @Date: 2018/9/21 14:03
 * @Description:
 */
public class Worker implements Callable<List<BaseCrawlBean>> {

    private static Map<String, String> requestUrls;

    private List<BaseCrawlBean> list = new LinkedList();

    private RequestProcessor requestProcessor;

    private Worker(){}

    public static Worker getInstance(RequestProcessor requestProcessor,String rootUrl){
        Worker worker = new Worker();
        worker.requestProcessor = requestProcessor;
        requestUrls = requestProcessor.loadLists(rootUrl);
        return worker;
    }

    /**
     * 可以使用cache替换
     */
    private Set<String> set = new HashSet<String>();

    @Override
    public List<BaseCrawlBean> call(){
        BaseCrawlBean bean;
        for(Map.Entry<String,String> entry : requestUrls.entrySet()){
            if(!set.contains(entry.getKey())){
                try {
                    bean = this.requestProcessor.doProcessor(entry.getKey(),entry.getValue());
                    set.add(entry.getKey());
                    list.add(bean);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
