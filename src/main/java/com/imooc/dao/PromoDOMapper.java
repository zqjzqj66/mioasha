package com.imooc.dao;

import com.imooc.dataobject.PromoDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PromoDOMapper {
    int deleteByPrimaryKey(Integer promoId);

    int insert(PromoDO record);

    int insertSelective(PromoDO record);

    PromoDO selectByPrimaryKey(Integer promoId);

    PromoDO selectByItemId(Integer itemId);

    int updateByPrimaryKeySelective(PromoDO record);

    int updateByPrimaryKey(PromoDO record);
}