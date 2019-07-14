package com.imooc.dao;

import com.imooc.dataobject.ItemDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ItemDOMapper {
    int deleteByPrimaryKey(Integer itemId);

    int insert(ItemDO record);

    int insertSelective(ItemDO record);

    ItemDO selectByPrimaryKey(Integer itemId);

    List<ItemDO> selectAll();

    int updateByPrimaryKeySelective(ItemDO record);

    int updateByPrimaryKey(ItemDO record);

    int increseSales(@Param("itemId") Integer itemId,@Param("amount")Integer amount);
}