package com.gyh.fleacampus.service;

import com.gyh.fleacampus.mapper.RoleMapper;
import com.gyh.fleacampus.model.Role;
import com.gyh.fleacampus.model.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gyh on 2021/2/7
 */
@Service
public class RoleService {
    @Resource
    RoleMapper roleMapper;
    private final Set<Role> roles = new HashSet<>();

    /**
     * 获取所有角色
     *
     * @return 角色集合
     */
    public Set<Role> getRoles() {
        if (roles.isEmpty()) {
            synchronized(RoleService.class) {
                if (roles.isEmpty()) {
                    roles.addAll(roleMapper.findAll());
                }
            }
        }
        return roles;
    }

    public Integer getRoleIdByName(String roleName) {
        return getRoles().stream()
                .filter(r -> r.getName().equals(roleName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("不存在该角色名:" + roleName))
                .getId();
    }

    /**
     * 给用户添加角色
     *
     * @param userId   用户id
     * @param roleName 角色id
     * @return 受影响行数
     */
    public Integer addRoleToUser(int userId, String roleName, Integer unitId) {
        Integer roleId = getRoleIdByName(roleName);
        return roleMapper.insert(userId, roleId, unitId);
    }

    public Integer updateRoleById(String roleName, Integer id, Integer unitId) {
        Integer roleId = getRoleIdByName(roleName);
        return roleMapper.updateRoleById(id, roleId, unitId);
    }

    public Integer updateRoleByUserIdAndUnitId(String roleName, Integer userId, Integer unitId) {
        Integer roleId = getRoleIdByName(roleName);
        return roleMapper.updateRoleByUserIdAndUnitId(userId, roleId, unitId);
    }

    /**
     * 给用户移除角色
     *
     * @param userId   用户id
     * @param roleName 角色id
     * @return 受影响行数
     */
    public Integer removeRoleToUser(int userId, String roleName, Integer unitId) {
        Integer roleId = getRoleIdByName(roleName);
        return roleMapper.removeRoleToUser(userId, roleId, unitId);
    }

    public List<UserRole> findByUserIdAndRoleId(Integer userId, String roleName) {
        Integer userRoleId = getRoleIdByName(roleName);
        List<UserRole> userRoles = roleMapper.findByUserIdAndRoleId(userId, userRoleId);
        return userRoles == null ? Collections.emptyList() : userRoles;
    }
}
