package com.gyh.fleacampus.mapper;


import com.gyh.fleacampus.model.Role;
import com.gyh.fleacampus.model.UserRole;

import java.util.List;

/**
 * Created by gyh on 2021/2/4
 */
public interface RoleMapper {
    List<Role> findRoleByUserId(Integer userId);

    Integer insert(Integer userId, Integer roleId, Integer unitId);

    List<Role> findAll();

    Integer findIdByName(String name);

    List<UserRole> findByUserIdAndRoleId(Integer userId, Integer roleId);

    Integer removeRoleToUser(Integer userId, Integer roleId, Integer unitId);

    Integer deleteById(Integer id);

    Integer updateRoleById(Integer id, Integer roleId, Integer unitId);

    Integer updateRoleByUserIdAndUnitId(Integer userId, Integer roleId, Integer unitId);
}
