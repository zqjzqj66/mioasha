package com.imooc.constant;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: ResponseStatusEnum
 * @date 2019/6/2 16:01
 */
public enum ResponseStatusEnum {

    SUCCESS(100),
    FAIL(200);

    private int code;

    private ResponseStatusEnum(int code){
        this.code=code;
    }

    public Integer getCode(){
        return code;
    }

}
