package zhengtoon.framework.task.core.controller;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zhengtoon.framework.task.core.dataresponse.ServerHttpResponse;
import zhengtoon.framework.task.core.dto.TaskCase;
import zhengtoon.framework.task.core.dto.TaskDTO;
import zhengtoon.framework.task.core.enums.CoreTaskEnum;
import zhengtoon.framework.task.core.service.TaskManageServer;

import java.util.List;

/**
 * @auther: qindaorong
 * @Date: 2018/7/13 16:04
 * @Description:
 */
@RestController
public class TaskManageController {

    private static final Logger logger = LoggerFactory.getLogger(TaskManageController.class);

    @Autowired
    private TaskManageServer taskManageServer;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ServerHttpResponse add(@RequestBody TaskDTO taskDTO) {
        ServerHttpResponse serverHttpResponse;
        try {
            taskManageServer.addNewTask(taskDTO);
            serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_OK, CoreTaskEnum.CORE_TASK_SEND_SUC.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, CoreTaskEnum.CORE_TASK_SEND_FAIL.getCode());
        }
        return serverHttpResponse;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ServerHttpResponse update(@RequestBody TaskDTO taskDTO) {
        ServerHttpResponse serverHttpResponse;
        try {
            taskManageServer.updateTask(taskDTO);
            serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_OK, CoreTaskEnum.CORE_TASK_SEND_SUC.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, CoreTaskEnum.CORE_TASK_SEND_FAIL.getCode());
        }
        return serverHttpResponse;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ServerHttpResponse delete(@RequestBody TaskDTO taskDTO) {
        ServerHttpResponse serverHttpResponse;
        try {
            taskManageServer.deleteTask(taskDTO);
            serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_OK, CoreTaskEnum.CORE_TASK_SEND_SUC.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, CoreTaskEnum.CORE_TASK_SEND_FAIL.getCode());
        }
        return serverHttpResponse;
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ServerHttpResponse find(@RequestBody TaskDTO taskDTO) {
        ServerHttpResponse serverHttpResponse;
        try {
            TaskCase taskCase = taskManageServer.findTask(taskDTO);
            serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_OK, CoreTaskEnum.CORE_TASK_SEND_SUC.getCode());
            serverHttpResponse.setMapAttribute("taskCase", taskCase);
        } catch (Exception e) {
            e.printStackTrace();
            serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, CoreTaskEnum.CORE_TASK_SEND_FAIL.getCode());

        }
        return serverHttpResponse;
    }

    @RequestMapping(value = "/queryTasks", method = RequestMethod.POST)
    public ServerHttpResponse queryTasks() {
        ServerHttpResponse serverHttpResponse;
        try {
            List<TaskCase> taskCaseList = taskManageServer.queryTasks();
            serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_OK, CoreTaskEnum.CORE_TASK_SEND_SUC.getCode());
            serverHttpResponse.setMapAttribute("taskCaseList", taskCaseList);
        } catch (Exception e) {
            e.printStackTrace();
            serverHttpResponse = new ServerHttpResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, CoreTaskEnum.CORE_TASK_SEND_FAIL.getCode());

        }
        return serverHttpResponse;
    }
}
