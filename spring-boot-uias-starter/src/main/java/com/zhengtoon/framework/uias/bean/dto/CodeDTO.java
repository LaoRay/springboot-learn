package com.zhengtoon.framework.uias.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


/**
 * code参数
 */
@Data
public class CodeDTO {
    //uias的code
    @ApiModelProperty(name = "code"  ,value ="uias code" , required = true)
    @NotBlank(message = "code参数不能空")
    private String code;
    //业务应用预留字段
    @ApiModelProperty(name = "reserved"  ,value ="业务预留字典 透传" )
    private String reserved;
}
