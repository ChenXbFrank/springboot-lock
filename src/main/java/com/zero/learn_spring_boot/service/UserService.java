package com.zero.learn_spring_boot.service;

import com.zero.learn_spring_boot.lock.redisson.UnableToAquireLockException;
import com.zero.learn_spring_boot.mapper.test01.UserMapper1;
import com.zero.learn_spring_boot.mapper.test02.UserMapper2;
import com.zero.learn_spring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserMapper1 userMapper1;

    @Autowired
    private UserMapper2 userMapper2;

    @Transactional
    public void addUser(User user){
        userMapper1.addUser(user.getName(),user.getAge());
        userMapper2.addUser(user.getName(),user.getAge());
        throw new UnableToAquireLockException("操作数据库报错啦！！！");
    }
}
