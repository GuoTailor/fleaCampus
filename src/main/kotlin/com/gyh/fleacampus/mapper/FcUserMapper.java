package com.gyh.fleacampus.mapper;

import com.gyh.fleacampus.model.FcUser;

/**
 * @Entity com.gyh.fleacampus.model.FcUser
 */
public interface FcUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FcUser record);

    int insertSelective(FcUser record);

    FcUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FcUser record);

    int updateByPrimaryKey(FcUser record);

}




