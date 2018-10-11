package com.zhengtoon.framework.demo.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhengtoon.framework.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: shenlong
 * @create: 2018/7/12  16:20
 * Copyright: Copyright (c) 2018
 * Company: 北京思源政通科技集团有限公司
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
