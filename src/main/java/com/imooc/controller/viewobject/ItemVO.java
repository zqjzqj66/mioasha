package com.imooc.controller.viewobject;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: ItemVO
 * @date 2019/6/5 10:14
 */
@Data
public class ItemVO {

    private Integer itemId;

    //wei1表示还没开始 为2表示正在进行中 为0表示没有活动
    private Integer status;

    private String  title;

    private BigDecimal price;

    private String description;

    private Integer stock;

    private Integer sales;

    private String imgUrl;


    private Integer promoStatus;

    private BigDecimal promoPrice;

    private Integer promoId;

    private LocalDateTime startTime;

    //private LocalDateTime endTime;

}
