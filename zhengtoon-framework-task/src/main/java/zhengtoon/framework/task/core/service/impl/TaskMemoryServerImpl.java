package zhengtoon.framework.task.core.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhengtoon.framework.task.core.components.TaskFactory;
import zhengtoon.framework.task.core.dto.TaskCase;
import zhengtoon.framework.task.core.dto.TaskDTO;
import zhengtoon.framework.task.core.service.TaskMemoryServer;

import java.util.List;

/**
 * @auther: qindaorong
 * @Date: 2018/7/16 09:37
 * @Description:
 */
@Service
public class TaskMemoryServerImpl implements TaskMemoryServer {

    @Autowired
    private TaskFactory taskFactory;

    @Override
    public Boolean addNewTask(TaskDTO taskDTO) {
        //add new Task to memory
        TaskCase taskCase= this.convort2TaskCase(taskDTO);
        return taskFactory.add(taskCase);
    }

    @Override
    public Boolean updateTask(TaskDTO taskDTO) {
        //update Task on memory
        TaskCase taskCase= this.convort2TaskCase(taskDTO);
        Boolean isExist = taskFactory.update(taskCase);
        return isExist;
    }

    @Override
    public void deleteTask(TaskDTO taskDTO) {
        taskFactory.delete(taskDTO.getTaskName());
    }

    @Override
    public TaskCase findTask(TaskDTO taskDTO) {
        return taskFactory.loadOne(taskDTO.getTaskName());
    }

    @Override
    public List<TaskCase> queryTasks() {
        return taskFactory.loadAll();
    }

    private TaskCase convort2TaskCase(TaskDTO taskDTO){
        TaskCase taskCase = new TaskCase();
        BeanUtils.copyProperties(taskDTO,taskCase);
        return taskCase;
    }
}
