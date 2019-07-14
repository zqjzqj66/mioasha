package com.imooc.dataobject;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDO {
    private String orderId;

    private Integer userId;

    private Integer itemId;

    private BigDecimal itemPrice;

    private Integer amount;

    private Integer promoId;
}