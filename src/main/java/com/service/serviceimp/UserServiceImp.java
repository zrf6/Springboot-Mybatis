package com.service.serviceimp;

import com.dao.UserMapper;
import com.entity.User;
import com.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
public class UserServiceImp implements UserService {

    private Logger log = LoggerFactory.getLogger(UserServiceImp.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> login(User user) {
        log.info("进入了login方法，参数为：" + user);
        List<User> users = userMapper.selectByUser(user);
        log.info("结束login方法，参数为：" + users);
        return users;
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    @Transactional
    public Integer saveUser(User user) {
        return userMapper.saveUser(user);
    }

    @Override
    @Transactional
    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    @Transactional
    public Integer deleteUser(User user) {
        return userMapper.deleteUser(user);
    }
}
