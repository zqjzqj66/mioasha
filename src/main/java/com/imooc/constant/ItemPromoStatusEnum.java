package com.imooc.constant;

import lombok.Getter;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: ItemPromoStatusEnum
 * @date 2019/6/16 10:52
 */
@Getter
public enum ItemPromoStatusEnum {

    START(1,"未开始"),
    ONGOING0(2,"正在进行"),
    NO(0,"没有活动");

    private Integer status;

    private String msg;

    ItemPromoStatusEnum(Integer status,String msg){
        this.status=status;
        this.msg=msg;
    }
}
