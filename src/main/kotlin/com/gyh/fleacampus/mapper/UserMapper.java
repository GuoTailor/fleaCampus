package com.gyh.fleacampus.mapper;


import com.gyh.fleacampus.model.User;

import java.util.List;

public interface UserMapper {
    User loadUserByUsername(String username);

    User findUserRoleById(Integer id);

    User findUserById(Integer id);

    List<User> findAll();

    int save(User user);

    Integer deleteUserById(Integer id);

    Integer update(User user);
}