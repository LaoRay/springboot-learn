package com.zhengtoon.framework.demo.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhengtoon.framework.demo.bean.dto.UserDTO;
import com.zhengtoon.framework.demo.bean.vo.UserVO;
import com.zhengtoon.framework.demo.entity.User;
import com.zhengtoon.framework.demo.mapper.UserMapper;
import com.zhengtoon.framework.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    //增加
    @Override
    public Integer insertUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        userMapper.insert(user);
        return user.getId();
    }

    //删除
    @Override
    public Integer deleteUser(Integer id) {
        return userMapper.deleteById(id);
    }

    //查询数据库
    @Override
    public UserVO select(Integer id) {
        User user = super.selectById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }

    //修改
    @Override
    public Integer update(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        return baseMapper.updateById(user);
    }

	@Override
	public Page<User> selectPage(UserDTO userDTO) {
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.where("name={0}",userDTO.getName());
        return super.selectPage(userDTO,ew);
	}
}
