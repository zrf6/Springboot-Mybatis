package com.service;

import com.entity.User;

import java.util.List;

public interface UserService {

    List<User> login(User user);

    List<User> selectAll();

    Integer saveUser(User user);

    Integer updateUser(User user);

    Integer deleteUser(User user);
}
