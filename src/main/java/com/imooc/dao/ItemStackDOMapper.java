package com.imooc.dao;

import com.imooc.dataobject.ItemStackDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemStackDOMapper {
    int deleteByPrimaryKey(Integer itemStockId);

    int insert(ItemStackDO record);

    int insertSelective(ItemStackDO record);

    ItemStackDO selectByPrimaryKey(Integer itemStockId);

    ItemStackDO selectByItemId(Integer itemId);

    List<ItemStackDO> selectAll();

    int updateByPrimaryKeySelective(ItemStackDO record);

    int updateByPrimaryKey(ItemStackDO record);

    int decreaseStock(@Param("itemId") Integer itemId,@Param("amount") Integer amount);
}