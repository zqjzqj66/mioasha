package com.imooc.dataobject;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class PromoDO {
    private Integer promoId;

    private String promoName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer itemId;

    private BigDecimal promoItemPrice;

}