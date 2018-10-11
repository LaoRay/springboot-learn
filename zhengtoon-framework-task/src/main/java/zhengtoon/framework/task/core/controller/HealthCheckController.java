package zhengtoon.framework.task.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: qindaorong
 * @Date: 2018/7/13 16:01
 * @Description:
 */
@RestController
public class HealthCheckController extends AbstractHealthController {

    @RequestMapping("/healthCheck")
    @ResponseBody
    @Override
    public String healthCheck() {
        return HealthCheckController.HEALTH_CHECK_OK;
    }
}
