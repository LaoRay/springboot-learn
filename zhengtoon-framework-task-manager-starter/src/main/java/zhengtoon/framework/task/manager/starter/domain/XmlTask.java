package zhengtoon.framework.task.manager.starter.domain;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * @Description:
 * @author:sanchuan.li
 * @date:2018/8/21
 * @version:1.0
 * @Copyright:2018 www.zhengtoon.com Inc. All rights reserved.
 * @Company:Beijing Siyuan Zhengtoon Technology Group Co., Ltd.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "task")
@Data
public class XmlTask {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "taskName")
    private String taskName;

    @XmlAttribute(name = "taskCron")
    private String taskCron;

    @XmlAttribute(name = "executeUrl")
    private String executeUrl;

    @XmlAttribute(name = "taskGroup")
    private String taskGroup;

}
