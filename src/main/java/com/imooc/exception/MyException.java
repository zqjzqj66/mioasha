package com.imooc.exception;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: MyException
 * @date 2019/6/2 16:13
 */
public class MyException extends RuntimeException implements CommonExceptionInterface{

    private int errCode;

    private String errMes;

    public MyException(ErrListEnum errListEnum){
        super(errListEnum.getErrMes());
        this.errCode=errListEnum.getErrCode();
        this.errMes=errListEnum.getErrMes();
    }

    public MyException(ErrListEnum errListEnum, String errMes) {
        errListEnum.setErrMes(errMes);
        this.errCode = errListEnum.getErrCode();
        this.errMes = errListEnum.getErrMes();
    }

    @Override
    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getErrMes() {
        return errMes;
    }
}
