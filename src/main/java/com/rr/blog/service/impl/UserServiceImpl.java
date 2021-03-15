package com.rr.blog.service.impl;

import com.rr.blog.entity.User;
import com.rr.blog.mapper.UserMapper;
import com.rr.blog.service.UserServive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@Slf4j
public class UserServiceImpl implements UserServive {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> listUser() {
        return userMapper.listUser();
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.update(user);
    }
    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteById(id);

    }

    @Override
    public User insertUser(User user) {
        user.setUserRegisterTime(new Date());
        return user;
    }

    @Override
    public User getUserByNameOrEmail(String str) {
        return userMapper.getUserByNameOrEmail(str);
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByName(email);
    }
}
