package com.zhengtoon.framework.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Data;

/**
 * entity基类，提供了公共的表字段
 * @author LuCheng.Qi
 * @since 2018-07-09
 * Company:北京思源政务通有限公司
 */
@Data
public class BaseEntity {

	/**
	 * 创建时间
     * 数据保存时会自动填充
     */
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    protected Long createTime;

    /**
     * 更新时间
     * 数据保存或更新时会自动填充
     */
    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    protected Long updateTime;

    /**
     * 逻辑删除标识
     * 框架自动处理
     */
    @TableField(value = "delete_flag")
    @TableLogic
    protected Integer deleteFlag;

}

