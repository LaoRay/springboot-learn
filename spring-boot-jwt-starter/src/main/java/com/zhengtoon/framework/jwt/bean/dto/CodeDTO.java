package com.zhengtoon.framework.jwt.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * code参数
 */
@Data
public class CodeDTO {
    //uias的code
    @ApiModelProperty(name = "code"  ,value ="uias code")
    private String code;
    //业务应用预留字段
    @ApiModelProperty(name = "reserved"  ,value ="业务预留字典透传")
    private String reserved;
}
