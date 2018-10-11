package zhengtoon.framework.task.manager.starter.service;

import zhengtoon.framework.task.manager.starter.dto.TaskDTO;

import java.util.List;


public interface TaskManageService {

    void addNewTask(TaskDTO taskDTO);

    void updateTask(TaskDTO taskDTO);

    void deleteTask(TaskDTO taskDTO);

    TaskDTO findTask(TaskDTO taskDTO);

    List<TaskDTO> queryTasks();
}
