package com.imooc.dao;

import com.imooc.dataobject.UserDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDo record);
    int insertSelective(UserDo record);

    UserDo selectByPrimaryKey(Integer id);

    UserDo selectByTelphone(String telphone);

    List<UserDo> selectAll();

    int updateByPrimaryKey(UserDo record);
}