package zhengtoon.framework.task.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import zhengtoon.framework.task.core.entity.Task;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2018-07-17
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {
    Task queryByTaskName(String taskName);
}
