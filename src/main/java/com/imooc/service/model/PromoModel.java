package com.imooc.service.model;

import com.imooc.constant.PromoStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: PromoModel
 * @date 2019/6/9 21:30
 */
@Data
public class PromoModel {

    //id
    private Integer promoId;

    //wei1表示还没开始 为2表示正在进行中 为3表示结束
    private PromoStatusEnum     status;

    //秒杀活动的名称
    private String promoName;

    //开始时间
    private LocalDateTime startTime;

    //结束时间
    private LocalDateTime endTime;

    //秒少的商品id
    private Integer itemId;

    //秒杀时候的商品的价格
    private BigDecimal promoItemPrice;
}
