package com.imooc.service;

import com.imooc.service.model.OrderModel;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: OrderService
 * @date 2019/6/9 17:08
 */
public interface OrderService {

    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount);
}
