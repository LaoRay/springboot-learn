package com.zhengtoon.framework.enums;

import lombok.Getter;

/**
 * @author: qindaorong
 * @Date: 2018/9/21 10:41
 * @Description:
 */
public enum ProcessorStatusEnums {

    PROCESSOR_INIT(0),
    PROCESSOR_RUNNING(1),
    PROCESSOR_STOPPED(2);

    @Getter
    private int status;

    ProcessorStatusEnums(int status) {
        this.status = status;
    }
}
