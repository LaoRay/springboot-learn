package zhengtoon.framework.task.manager.starter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhengtoon.framework.task.manager.starter.components.TaskFactory;
import zhengtoon.framework.task.manager.starter.dto.TaskDTO;
import zhengtoon.framework.task.manager.starter.service.TaskMemoryService;

import java.util.List;

@Service
@Slf4j
public class TaskMemoryServiceImpl implements TaskMemoryService {


    @Autowired
    private TaskFactory taskFactory;

    @Override
    public Boolean addNewTask(TaskDTO taskDTO) {
        //add new Task to memory
        TaskDTO taskCase= this.convort2TaskCase(taskDTO);
        return taskFactory.add(taskCase);
    }

    @Override
    public Boolean updateTask(TaskDTO taskDTO) {
        //update Task on memory
        TaskDTO taskCase= this.convort2TaskCase(taskDTO);
        Boolean isExist = taskFactory.update(taskCase);
        return isExist;
    }

    @Override
    public void deleteTask(TaskDTO taskDTO) {
        taskFactory.delete(taskDTO.getTaskName());
    }

    @Override
    public TaskDTO findTask(TaskDTO taskDTO) {
        return taskFactory.loadOne(taskDTO.getTaskName());
    }

    @Override
    public List<TaskDTO> queryTasks() {
        return taskFactory.loadAll();
    }

    private TaskDTO convort2TaskCase(TaskDTO taskDTO){
        TaskDTO taskCase = new TaskDTO();
        BeanUtils.copyProperties(taskDTO,taskCase);
        return taskCase;
    }

}
