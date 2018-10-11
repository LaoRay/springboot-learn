package zhengtoon.framework.task.core.service;

import com.baomidou.mybatisplus.service.IService;
import zhengtoon.framework.task.core.dto.TaskCase;
import zhengtoon.framework.task.core.dto.TaskDTO;
import zhengtoon.framework.task.core.entity.Task;

import java.util.List;

/**
 * @auther: qindaorong
 * @Date: 2018/7/16 09:36
 * @Description:
 */
public interface TaskManageServer extends IService<Task> {

    void addNewTask(TaskDTO taskDTO);

    void updateTask(TaskDTO taskDTO);

    void deleteTask(TaskDTO taskDTO);

    TaskCase findTask(TaskDTO taskDTO);

    List<TaskCase> queryTasks();
}
