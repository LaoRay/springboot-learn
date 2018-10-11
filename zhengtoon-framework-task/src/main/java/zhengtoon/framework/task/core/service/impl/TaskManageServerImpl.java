package zhengtoon.framework.task.core.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import zhengtoon.framework.task.core.dto.TaskCase;
import zhengtoon.framework.task.core.dto.TaskDTO;
import zhengtoon.framework.task.core.entity.Task;
import zhengtoon.framework.task.core.mapper.TaskMapper;
import zhengtoon.framework.task.core.service.TaskManageServer;
import zhengtoon.framework.task.core.service.TaskMemoryServer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther: qindaorong
 * @Date: 2018/7/16 09:37
 * @Description:
 */
@Service
public class TaskManageServerImpl  extends ServiceImpl<TaskMapper, Task> implements TaskManageServer {

    private static final Logger logger = LoggerFactory.getLogger(TaskManageServerImpl.class);

    @Autowired
    private TaskMemoryServer taskMemoryServer;

    @Resource
    private TaskMapper taskMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addNewTask(TaskDTO taskDTO) {
        //add new Task to memory
        Boolean isNew = taskMemoryServer.addNewTask(taskDTO);
        logger.debug("[TaskManageServerImpl][addNewTask] has add an new task into memory");

        //asynchronous add Task to database
        if(isNew){
            this.add(taskDTO);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateTask(TaskDTO taskDTO) {
        // update Task on memory
        Boolean isExist = taskMemoryServer.updateTask(taskDTO);

        //asynchronous update to database
        if(isExist){
            this.update(taskDTO);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteTask(TaskDTO taskDTO) {
        // delete Task on memory
        taskMemoryServer.deleteTask(taskDTO);

        //asynchronous remove to database
        this.delete(taskDTO);
    }

    @Override
    public TaskCase findTask(TaskDTO taskDTO) {
        //find on memory
        TaskCase taskCase = taskMemoryServer.findTask(taskDTO);
        //if null find in database,asynchronous add to database
        if(taskCase == null){
            Task task = taskMapper.queryByTaskName(taskDTO.getTaskName());
            taskCase = new TaskCase();
            BeanUtils.copyProperties(task,taskCase);
        }
        return  taskCase;
    }

    @Override
    public List<TaskCase> queryTasks() {
        //find all task on memory
        List<TaskCase> taskCaseList = taskMemoryServer.queryTasks();
        if(!CollectionUtils.isEmpty(taskCaseList)){
            return  taskCaseList;
        }else{
            Condition condition = new Condition();
            condition.orderBy(Boolean.TRUE,"id",Boolean.FALSE);
            List<Task> taskList  = super.selectList(condition);

            taskCaseList = this.convort2TaskCaseList(taskList);
            return  taskCaseList;
        }
    }

    private Task convort2Task(TaskDTO taskDTO){
        Task task = new Task();
        BeanUtils.copyProperties(taskDTO,task);
        return task;
    }

    private List<TaskCase> convort2TaskCaseList(List<Task> taskList ){
        List<TaskCase> taskCaseList = new ArrayList<TaskCase>();
        TaskCase taskCase = null;
        for(Task task : taskList){
            taskCase = new TaskCase();
            BeanUtils.copyProperties(task,taskCase);
            taskCaseList.add(taskCase);
        }
        return taskCaseList;
    }

    Task add(TaskDTO taskDTO){
        logger.debug("[TaskManageServerImpl][add] start to add task to db");
        Task task = this.convort2Task(taskDTO);
        super.insert(task);
        logger.debug("[TaskManageServerImpl][add] success to add ");
        return task;
    }

    Task update(TaskDTO taskDTO){
        logger.debug("[TaskManageServerImpl][update] start to update task to db");
        Task oldTask = taskMapper.queryByTaskName(taskDTO.getTaskName());
        Task task = this.convort2Task(taskDTO);
        task.setId(oldTask.getId());
        super.updateById(task);
        logger.debug("[TaskManageServerImpl][update] success to update ");
        return task;
    }

    void delete(TaskDTO taskDTO){
        logger.debug("[TaskManageServerImpl][delete] start to delete task to db");
        Task oldTask = taskMapper.queryByTaskName(taskDTO.getTaskName());
        Long id = oldTask.getId();
        super.deleteById(id);
        logger.debug("[TaskManageServerImpl][delete] success to add ");
    }
}
