package zhengtoon.framework.task.manager.starter.domain;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @Description:
 * @author:sanchuan.li
 * @date:2018/8/21
 * @version:1.0
 * @Copyright:2018 www.zhengtoon.com Inc. All rights reserved.
 * @Company:Beijing Siyuan Zhengtoon Technology Group Co., Ltd.
 */
@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class XmlTaskList {

    @XmlElement(name = "task")
    private List<XmlTask> xmlTaskList;
}
