package zhengtoon.framework.task.starter.service;

import zhengtoon.framework.task.starter.dataresponse.ServerHttpResponse;
import zhengtoon.framework.task.starter.dto.TaskDTO;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 17:18
 * @Description:
 */
public interface TaskManagerServer {

    ServerHttpResponse addNewTask(TaskDTO taskDTO);

    ServerHttpResponse updateTask(TaskDTO taskDTO);

    ServerHttpResponse deleteTask(TaskDTO taskDTO);

    ServerHttpResponse findTask(TaskDTO taskDTO);

    ServerHttpResponse queryTasks();
}
