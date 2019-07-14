package com.imooc.exception;

import com.imooc.response.CommonReturnType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: ResponseExceptionHandler
 * @date 2019/6/2 16:34
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(Exception.class)
    public CommonReturnType handler(Exception e) {
        HashMap<String, Object> map = new HashMap<>();
        if (e instanceof MyException) {
            MyException my = (MyException) e;
            log.info(my.getErrCode() + "  " + my.getErrMes());
            map.put("errMes", my.getErrMes());
            map.put("errCode", my.getErrCode());
        } else {
            ErrListEnum unknownError=ErrListEnum.UNKNOWN_ERROR;
            log.info("未知错误："+e.getMessage());
            log.info(unknownError.getErrCode() + "  " + unknownError.getErrMes());
            map.put("errMes", unknownError.getErrMes());
            map.put("errCode", unknownError.getErrCode());
        }
        return CommonReturnType.fail(map);
    }

}
