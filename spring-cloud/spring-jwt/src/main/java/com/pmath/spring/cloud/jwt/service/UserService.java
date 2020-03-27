package com.pmath.spring.cloud.jwt.service;

import com.pmath.spring.cloud.jwt.dto.User;
import com.pmath.spring.cloud.jwt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserService {
    @Autowired
    UserMapper userMapper;
    public User findUserById(String id){
        return userMapper.selectByPrimaryKey(id);
    }
    public User findByUsername(User user){
        return userMapper.findByUsername(user.getUsername());
    }
}
