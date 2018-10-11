package zhengtoon.framework.task.starter.components;

import lombok.extern.slf4j.Slf4j;
import zhengtoon.framework.task.starter.dto.HttpConnDto;
import zhengtoon.framework.task.starter.worker.Worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 19:44
 * @Description:
 */
@Slf4j
public class HealthWatcher {

    private ExecutorService executorService;

    public HealthWatcher (HttpConnDto httpConnDto){
        log.debug("start init HealthWatcher ExecutorService pool ");
        executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        this.checkHealth(httpConnDto);
        log.debug("end init HealthWatcher ExecutorService pool ");
    }

    private void checkHealth(HttpConnDto httpConnDto){
        log.debug("ready health thread");
        Worker worker = new Worker(httpConnDto);
        executorService.execute(worker);
        log.debug("start health thread ");
    }

}