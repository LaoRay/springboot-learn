package com.zhengtoon.framework.demo.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhengtoon.framework.demo.bean.dto.UserDTO;
import com.zhengtoon.framework.demo.bean.vo.UserVO;
import com.zhengtoon.framework.demo.entity.User;

public interface UserService {
    //增加
    Integer insertUser(UserDTO userVO);
    //删除
    Integer deleteUser(Integer id);
    //查询
    UserVO select(Integer id);
    //修改
    Integer update(UserDTO userVO);
    //分页查询
	Page<User> selectPage(UserDTO userVO);

}
