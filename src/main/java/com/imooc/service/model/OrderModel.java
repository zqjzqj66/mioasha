package com.imooc.service.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: OrderModel
 * @date 2019/6/9 16:03
 */
@Data
public class OrderModel {

    //企业级的订单 一般都是使用string的  原因有两个
    //订单的数字太大
    //id号一般有一定的含义 201802020001之类的
    private String orderId;


    private Integer userId;

    private Integer itemId;

    private Integer amount;

    //这里要有一个price  因为订单是一条信息 price可能一直在变 但是我买的时候 就是这个价格
    private BigDecimal itemPrice;

    //诺非空则是秒杀下单
    private Integer promoId;

    //诺非空则是秒杀价格下单
    private BigDecimal promoPrice;
}
