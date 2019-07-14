package com.imooc.service.impl;

import com.imooc.constant.PromoStatusEnum;
import com.imooc.dao.OrderDOMapper;
import com.imooc.dao.SequenceDOMapper;
import com.imooc.dataobject.OrderDO;
import com.imooc.dataobject.SequenceDO;
import com.imooc.exception.ErrListEnum;
import com.imooc.exception.MyException;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import com.imooc.service.PromoService;
import com.imooc.service.UserService;
import com.imooc.service.model.ItemModel;
import com.imooc.service.model.OrderModel;
import com.imooc.service.model.PromoModel;
import com.imooc.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: OrderServiceImpl
 * @date 2019/6/9 17:09
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private PromoService promoService;


    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId,Integer promoId,Integer amount) {
        //验证用户和商品
        ItemModel itemById = itemService.getItemById(itemId);
        if(itemById==null){
            throw new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        }

        UserModel userById = userService.getUserById(userId);
        if(userById==null){
            throw new MyException(ErrListEnum.USER_NO_EXIST);
        }

        if(amount.intValue()<0 || amount.intValue() >10){
            throw new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR,"订单数量超出限制");
        }

        //订单去库存 支付去库存 但是会有超卖的风险
        boolean b = itemService.decreaseStock(itemId, amount);
        if (b==false){
            throw new MyException(ErrListEnum.STOCK_NOT_ENOUGH);
        }

        //生成订单号
        String id = generateOrderNumber();
        OrderDO orderDO = new OrderDO();
        orderDO.setOrderId(id);
        orderDO.setAmount(amount);
        orderDO.setItemPrice(itemById.getPrice());
        orderDO.setItemId(itemById.getItemId());
        orderDO.setUserId(userId);

        //检查订单的活动信息是否正确
        if (promoId!=null){
            PromoModel promoByItemId = promoService.getPromoByItemId(itemId);

            if(promoByItemId==null || promoByItemId.getItemId().intValue()!=itemId.intValue()
                    ||promoByItemId.getStatus()!= PromoStatusEnum.ONGOING0)
                throw  new MyException(ErrListEnum.PROMO_INFO_ERR);
            else{
                orderDO.setItemPrice(promoByItemId.getPromoItemPrice());
                orderDO.setPromoId(promoId);
            }

        }

        //生成订单 保存
        orderDOMapper.insertSelective(orderDO);

        itemService.increaseSales(itemId,amount );
        //返回订单对象
        OrderDO orderDO1 = orderDOMapper.selectByPrimaryKey(id);

        OrderModel orderModel = convertMdelFormDO(orderDO1);
        return orderModel;
    }


    private OrderModel convertMdelFormDO(OrderDO orderDO){
        if (orderDO==null){
            throw new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR);
        }
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderDO,orderModel );
        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String generateOrderNumber(){
        //order是一个16位的字符串
        //前8个字符 年月日
        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        String nowStr= now.format(DateTimeFormatter.ISO_LOCAL_DATE).replace("-","" );
        stringBuilder.append(nowStr);
        //中间6个是自增序列
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");

        int currentValue = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(currentValue+sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String s = String.valueOf(currentValue);

        for (int i = 0; i <6-s.length() ; i++) {
            stringBuilder.append("0");
        }
        stringBuilder.append(s);
        //最后2位是分库分表  暂时写死
        stringBuilder.append("00");
        return stringBuilder.toString();
    }
}
