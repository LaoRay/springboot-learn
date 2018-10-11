package zhengtoon.framework.task.manager.starter.service;

import zhengtoon.framework.task.manager.starter.dto.TaskDTO;

import java.util.List;

public interface TaskMemoryService {

    Boolean addNewTask(TaskDTO taskDTO);

    Boolean updateTask(TaskDTO taskDTO);

    void deleteTask(TaskDTO taskDTO);

    TaskDTO findTask(TaskDTO taskDTO);

    List<TaskDTO> queryTasks();
}
