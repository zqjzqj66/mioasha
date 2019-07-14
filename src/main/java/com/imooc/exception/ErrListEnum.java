package com.imooc.exception;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: ErrListEnum
 * @date 2019/6/2 16:44
 */
public enum ErrListEnum implements CommonExceptionInterface {

    //通用错误类型
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),

    UNKNOWN_ERROR(10002,"未知错误"),

    //以20000开头的是定义用户相关的错误信息
    USER_NO_EXIST(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户名或密码不正确"),
    USER_NOT_LOGIN(20003,"用户还未登陆"),
    USER_LOGING_EXPIRED(20004,"用户登录信息已经失效，请重新登录"),
    USER_TOKEN_ILLEGAL(20005,"用户token非法，请重新登录"),

    //30000开头的是定义插入操作失败的
    CREATE_FAIL(30001,"插入操作失败"),


    //40000开头是交易信息错误
    STOCK_NOT_ENOUGH(40001,"库存不足"),
    PROMO_INFO_ERR(40002,"秒杀信息错误")
    ;





    private int errCode;

    private String errMes;



    ErrListEnum(int errCode, String errMes) {
        this.errCode=errCode;
        this.errMes=errMes;
    }

    @Override
    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getErrMes() {
        return errMes;
    }

    public void setErrMes(String errMes){
        this.errMes=errMes;
    }
}
