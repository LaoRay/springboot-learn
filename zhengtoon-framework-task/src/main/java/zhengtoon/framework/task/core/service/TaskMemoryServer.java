package zhengtoon.framework.task.core.service;

import zhengtoon.framework.task.core.dto.TaskCase;
import zhengtoon.framework.task.core.dto.TaskDTO;

import java.util.List;

/**
 * @auther: qindaorong
 * @Date: 2018/7/16 09:36
 * @Description:
 */
public interface TaskMemoryServer {

    Boolean addNewTask(TaskDTO taskDTO);

    Boolean updateTask(TaskDTO taskDTO);

    void deleteTask(TaskDTO taskDTO);

    TaskCase findTask(TaskDTO taskDTO);

    List<TaskCase> queryTasks();
}
