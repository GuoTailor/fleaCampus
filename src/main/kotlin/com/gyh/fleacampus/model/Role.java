package com.gyh.fleacampus.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

/**
 * Created by gyh on 2021/2/4
 */
public class Role implements GrantedAuthority {
    // 超级管理员
    public static final String SUPER_ADMIN = "ROLE_SUPER_ADMIN";
    // 管理员
    public static final String ADMIN = "ROLE_ADMIN";
    // 用户
    public static final String USER = "ROLE_USER";

    private Integer id;
    private String name;
    private String nameZh;
    private Integer unitId;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
