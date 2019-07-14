package com.imooc.constant;

import lombok.Getter;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: PromoStatusEnum
 * @date 2019/6/16 10:22
 */
@Getter
public enum PromoStatusEnum {
    START(1,"未开始"),
    ONGOING0(2,"正在进行"),
    END(3,"结束");

    private Integer status;

    private String msg;

    PromoStatusEnum(Integer status,String msg){
        this.status=status;
        this.msg=msg;
    }
}
