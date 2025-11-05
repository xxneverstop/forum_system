package com.honortech.forum_system.dao;

import com.honortech.forum_system.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int insert(User row);

    int insertSelective(User row);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);

    // @Param 指定希望在 XML 中用该名字引用参数
    User selectByUsername(@Param("username") String username);
}