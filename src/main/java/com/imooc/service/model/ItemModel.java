package com.imooc.service.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: ItemModel
 * @date 2019/6/4 21:22
 */
@Data
public class ItemModel {

    private Integer itemId;

    private PromoModel promoModel;

    @NotBlank(message = "商品名不能为空")
    private String  title;

    @NotNull(message = "价格不能为空")
    @Min(value = 0,message = "价格要大于0")
    private BigDecimal price;

    @NotBlank(message = "商品描述不能为空0")
    private String description;

    private Integer stock;

    private Integer sales;

    @NotBlank(message = "图片信息不能为空")
    private String imgUrl;


}
