package com.imooc.service;

import com.imooc.service.model.ItemModel;

import java.util.List;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: ItemService
 * @date 2019/6/4 22:21
 */
public interface ItemService {

    //创建商品
    ItemModel createItem(ItemModel itemModel);

    //商品信息浏览
    List<ItemModel> listItem();

    //获取商品详细信息
    ItemModel getItemById(Integer id);

    boolean decreaseStock(Integer itemId, Integer amount);

    void increaseSales(Integer itemId,Integer sales);
}
