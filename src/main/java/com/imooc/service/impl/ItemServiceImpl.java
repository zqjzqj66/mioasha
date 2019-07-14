package com.imooc.service.impl;

import com.imooc.constant.PromoStatusEnum;
import com.imooc.dao.ItemDOMapper;
import com.imooc.dao.ItemStackDOMapper;
import com.imooc.dataobject.ItemDO;
import com.imooc.dataobject.ItemStackDO;
import com.imooc.exception.ErrListEnum;
import com.imooc.exception.MyException;
import com.imooc.service.ItemService;
import com.imooc.service.PromoService;
import com.imooc.service.model.ItemModel;
import com.imooc.service.model.PromoModel;
import com.imooc.validator.ValidationResult;
import com.imooc.validator.ValidatorImpl;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: ItemServiceImpl
 * @date 2019/6/4 22:25
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStackDOMapper itemStackDOMapper;

    @Autowired
    private PromoService promoService;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) {
        //入参检验 这里不用判断itemmodel了 因为上面的使用set设置的
        //如果是直接使用了spring的参数封装的话，还是要判断的
       /* ValidationResult validate = ValidatorImpl.validate(itemModel);
        if (validate.isHasErrors()){
            throw  new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR,validate.getErrorMsg());
        }*/

        //model >>> dataObject
        ItemDO itemDO = convertItemFormMOdel(itemModel);

        //DO 插入数据库
        itemDOMapper.insertSelective(itemDO);

        System.out.println(itemDO.getItemId());
        itemModel.setItemId(itemDO.getItemId());
        ItemStackDO itemStackDO = convertItemStockFormMOdel(itemModel);
        itemStackDOMapper.insertSelective(itemStackDO);

        return getItemById(itemDO.getItemId());
    }

    //获取商品列表
    @Override
    public List<ItemModel> listItem() {
        //获取itemDO列表
        List<ItemDO> itemDOS = itemDOMapper.selectAll();

        List<ItemModel> list=itemDOS.stream().map(t->{
            ItemStackDO itemStackDO = itemStackDOMapper.selectByItemId(t.getItemId());
            ItemModel itemModel= convertModelFormDO(t, itemStackDO);
            return itemModel;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public ItemModel getItemById(Integer itemId) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(itemId);

        //当查询出来的为null的话 表示没有 不用报错 返回null
        if (itemDO==null)
            return null;
        ItemStackDO itemStackDO = itemStackDOMapper.selectByItemId(itemId);
        ItemModel itemModel = convertModelFormDO(itemDO, itemStackDO);

        //获取商品的活动
        PromoModel promoByItemId = promoService.getPromoByItemId(itemModel.getItemId());
        //没有活动 和活动已经结束
        if(promoByItemId==null || promoByItemId.getStatus()== PromoStatusEnum.END)
            promoByItemId=null;
        itemModel.setPromoModel(promoByItemId);
        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int row= itemStackDOMapper.decreaseStock(itemId,amount);
        return row==0?false:true;
    }

    @Override
    public void increaseSales(Integer itemId, Integer sales) {
        itemDOMapper.increseSales(itemId,sales);
    }

    //从itemDo和ItemStackDo里面获取ItemModel
    private static ItemModel convertModelFormDO(ItemDO itemDO,ItemStackDO itemStackDO){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel );
        itemModel.setStock(itemStackDO.getStock());
        return itemModel;
    }

    //从model里面获取ItemDO
    private static ItemDO convertItemFormMOdel(ItemModel itemModel) {
        if (itemModel==null)
            throw  new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR);
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel, itemDO);
        return itemDO;
    }

    //从model里面获取ItemStackDO
    private static ItemStackDO  convertItemStockFormMOdel(ItemModel itemModel) {
        if (itemModel==null)
            throw  new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR);
        ItemStackDO itemStackDO = new ItemStackDO();
        itemStackDO.setItemId(itemModel.getItemId());
        itemStackDO.setStock(itemModel.getStock());
        return itemStackDO ;
    }
}
