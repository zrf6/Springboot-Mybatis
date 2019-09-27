package com.dao;

import com.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> selectByUser(User user);

    List<User> selectAll();

    Integer saveUser(User user);

    Integer updateUser(User user);

    Integer deleteUser(User user);

}
