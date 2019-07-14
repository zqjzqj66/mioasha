package com.imooc.dataobject;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDO {

    private Integer itemId;

    private String title;

    private BigDecimal price;

    private String description;

    private String imgUrl;

    private Integer sales;
}