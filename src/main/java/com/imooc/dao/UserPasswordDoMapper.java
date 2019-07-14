package com.imooc.dao;

import com.imooc.dataobject.UserPasswordDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserPasswordDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPasswordDo record);
    int insertSelective(UserPasswordDo record);

    UserPasswordDo selectByUserId(Integer id);

    UserPasswordDo selectByPrimaryKey(Integer id);

    List<UserPasswordDo> selectAll();

    int updateByPrimaryKey(UserPasswordDo record);
}