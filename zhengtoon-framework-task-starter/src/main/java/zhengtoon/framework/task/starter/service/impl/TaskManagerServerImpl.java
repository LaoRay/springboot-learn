package zhengtoon.framework.task.starter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhengtoon.framework.task.starter.dataresponse.ServerHttpResponse;
import zhengtoon.framework.task.starter.dto.HttpConnDto;
import zhengtoon.framework.task.starter.dto.TaskDTO;
import zhengtoon.framework.task.starter.enums.CoreActionEnum;
import zhengtoon.framework.task.starter.exceptions.ServerAliveException;
import zhengtoon.framework.task.starter.exceptions.TaskHttpException;
import zhengtoon.framework.task.starter.exceptions.TaskManageException;
import zhengtoon.framework.task.starter.service.BaseTaskHttpService;
import zhengtoon.framework.task.starter.service.TaskManagerServer;

import javax.annotation.PostConstruct;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther: qindaorong
 * @Date: 2018/7/12 17:31
 * @Description:
 */
@Service
@Slf4j
public class TaskManagerServerImpl extends BaseTaskHttpService implements TaskManagerServer {

    @Autowired
    HttpConnDto httpConnDto;

    Map<String ,String> headerParams = new HashMap<String ,String>(3);

    @PostConstruct
    public void init(){
        if(httpConnDto == null ){
            log.warn("can not connect task manage.");
        }else{
            headerParams.put(TASK_NAME,httpConnDto.getTaskManageUserName());
            headerParams.put(TASK_CODE,httpConnDto.getTaskManagePassword());
            headerParams.put(HEAD_CONTENT_TYPE,HEAD_CONTENT_TYPE_VALUE);
        }
    }

    @Override
    public ServerHttpResponse addNewTask(TaskDTO taskDTO) {
        ServerHttpResponse serverHttpResponse = null;
        try {
            String serverPrefix = httpConnDto.getTaskManageAddress();
            String requestMapping = CoreActionEnum.add.getActionUrl();
            Map<String ,Object> params = this.transBean2Map(taskDTO);
            serverHttpResponse = super.sendHttpRequest(serverPrefix,requestMapping,params,headerParams,HTTP_METHODS_POST);
        } catch (ServerAliveException e) {
            serverHttpResponse= ServerAliveException.getServerAliveExceptionResponse();
        } catch (TaskHttpException e) {
            serverHttpResponse= TaskHttpException.getTaskHttpExceptionResponse();
        }catch (TaskManageException e){
            serverHttpResponse= TaskManageException.getTaskManageExceptionResponse();
        }
        return serverHttpResponse;
    }

    @Override
    public ServerHttpResponse updateTask(TaskDTO taskDTO) {
        ServerHttpResponse serverHttpResponse = null;
        try {
            String serverPrefix = httpConnDto.getTaskManageAddress();
            String requestMapping = CoreActionEnum.update.getActionUrl();
            Map<String ,Object> params = this.transBean2Map(taskDTO);
            serverHttpResponse =  super.sendHttpRequest(serverPrefix,requestMapping,params,headerParams,HTTP_METHODS_POST);
        } catch (ServerAliveException e) {
            serverHttpResponse= ServerAliveException.getServerAliveExceptionResponse();
        } catch (TaskHttpException e) {
            serverHttpResponse= TaskHttpException.getTaskHttpExceptionResponse();
        }catch (TaskManageException e){
            serverHttpResponse= TaskManageException.getTaskManageExceptionResponse();
        }
        return serverHttpResponse;
    }

    @Override
    public ServerHttpResponse deleteTask(TaskDTO taskDTO) {
        ServerHttpResponse serverHttpResponse = null;
        try {
            String serverPrefix = httpConnDto.getTaskManageAddress();
            String requestMapping = CoreActionEnum.delete.getActionUrl();
            Map<String ,Object> params = this.transBean2Map(taskDTO);
            serverHttpResponse = super.sendHttpRequest(serverPrefix,requestMapping,params,headerParams,HTTP_METHODS_POST);
        } catch (ServerAliveException e) {
            serverHttpResponse= ServerAliveException.getServerAliveExceptionResponse();
        } catch (TaskHttpException e) {
            serverHttpResponse= TaskHttpException.getTaskHttpExceptionResponse();
        }catch (TaskManageException e){
            serverHttpResponse= TaskManageException.getTaskManageExceptionResponse();
        }
        return serverHttpResponse;
    }

    @Override
    public ServerHttpResponse findTask(TaskDTO taskDTO) {
        ServerHttpResponse serverHttpResponse = null;
        try {
            String serverPrefix = httpConnDto.getTaskManageAddress();
            String requestMapping = CoreActionEnum.find.getActionUrl();
            Map<String ,Object> params = this.transBean2Map(taskDTO);
            serverHttpResponse = super.sendHttpRequest(serverPrefix,requestMapping,params,headerParams,HTTP_METHODS_POST);
        } catch (ServerAliveException e) {
            serverHttpResponse= ServerAliveException.getServerAliveExceptionResponse();
        } catch (TaskHttpException e) {
            serverHttpResponse= TaskHttpException.getTaskHttpExceptionResponse();
        }catch (TaskManageException e){
            serverHttpResponse= TaskManageException.getTaskManageExceptionResponse();
        }
        return serverHttpResponse;
    }

    @Override
    public ServerHttpResponse queryTasks() {
        ServerHttpResponse serverHttpResponse = null;
        try {
            String serverPrefix = httpConnDto.getTaskManageAddress();
            String requestMapping = CoreActionEnum.queryTasks.getActionUrl();
            Map<String ,Object> params = null;

            serverHttpResponse = super.sendHttpRequest(serverPrefix,requestMapping,params,headerParams,HTTP_METHODS_POST);
        } catch (ServerAliveException e) {
            serverHttpResponse= ServerAliveException.getServerAliveExceptionResponse();
        } catch (TaskHttpException e) {
            serverHttpResponse= TaskHttpException.getTaskHttpExceptionResponse();
        } catch (TaskManageException e){
            serverHttpResponse= TaskManageException.getTaskManageExceptionResponse();
        }
        return serverHttpResponse;
    }

    private  Map<String, Object> transBean2Map(Object obj) {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }
}
