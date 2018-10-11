package com.zhengtoon.framework.processor;

import com.zhengtoon.framework.bean.RequestProcessor;
import com.zhengtoon.framework.bean.Site;
import com.zhengtoon.framework.enums.ProcessorStatusEnums;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author: qindaorong
 * @Date: 2018/9/21 09:02
 * @Description:
 */
public abstract class CrawlFrameWorkProcessor implements ProcessorOperate {

    private ThreadPoolExecutor executorService;

    private Integer frameWorkStatus = ProcessorStatusEnums.PROCESSOR_INIT.getStatus();

    private Map<String, List<?>> processorValueMap;

    protected Site site;

    private Boolean checkRunningStat() {
        if (ProcessorStatusEnums.PROCESSOR_INIT.getStatus()== this.frameWorkStatus  || ProcessorStatusEnums.PROCESSOR_STOPPED.getStatus()== this.frameWorkStatus) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private void initComponents() {
        if (CollectionUtils.isEmpty(site.getProcessorMap())) {
            throw new RuntimeException("[site][processorMap] must not be null!");
        }

        int nThreads = site.getProcessorMap().size();
        processorValueMap = new ConcurrentHashMap<>(nThreads);
        this.initExecutor(nThreads);
        frameWorkStatus = ProcessorStatusEnums.PROCESSOR_INIT.getStatus();
    }


    private void initExecutor(int nThreads){
        executorService = new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    private void startWork() {
        Map<String, RequestProcessor> moduleUrlMap = site.getProcessorMap();
        for (Map.Entry<String, RequestProcessor> entry : moduleUrlMap.entrySet()) {

            frameWorkStatus = ProcessorStatusEnums.PROCESSOR_RUNNING.getStatus();

            RequestProcessor requestProcessor = entry.getValue();
            Future<?> future = executorService.submit(Worker.getInstance(requestProcessor,entry.getKey()));
            try {
                List<?> list = (List<?>) future.get();

                if (!CollectionUtils.isEmpty(list)) {
                    processorValueMap.put(entry.getKey(), list);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        //executor shutdown and reInit executor
        executorService.shutdown();
        int nThreads = site.getProcessorMap().size();
        this.initExecutor(nThreads);

        if(executorService.isTerminated()){
            frameWorkStatus = ProcessorStatusEnums.PROCESSOR_STOPPED.getStatus();
        }
    }

    @Override
    public void init(Site site) {
        this.site = site;
        this.initComponents();
    }

    @Override
    public void start() {
        Boolean isReady = this.checkRunningStat();
        if(isReady){
            this.startWork();
        }else{
            throw new RuntimeException("[processor] is not ready to run!");
        }
    }

    @Override
    public Boolean isReady() {
        return this.checkRunningStat();
    }

    @Override
    public void stop() {
       if(!executorService.isTerminated()){
           executorService.shutdownNow();
       }
    }

    @Override
    public void reset() {
        if(!executorService.isTerminated()){
            executorService.shutdownNow();
        }
        this.initComponents();
    }

    @Override
    public List<?> loadProcessorResultByKey(String rootUrl){
        if(processorValueMap.containsKey(rootUrl)){
            return  processorValueMap.get(rootUrl);
        }
        return null;
    }
}
