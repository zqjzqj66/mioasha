package com.imooc.response;

import com.imooc.constant.ResponseStatusEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: CommonReturnType
 * @date 2019/6/2 15:59
 */
public class CommonReturnType {

    private int code;

    private Object data;

    public static CommonReturnType success(){
        return success(null);
    }

    public static CommonReturnType success(String msg, Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put(msg,object);
        return success(map);
    }

    public static CommonReturnType success(Object data){
        return new CommonReturnType(100,data);
    }

    public static CommonReturnType fail(){
        return  new CommonReturnType(ResponseStatusEnum.FAIL.getCode(),null);
    }
    public static CommonReturnType fail(Object data){
        return  new CommonReturnType(ResponseStatusEnum.FAIL.getCode(),data);
    }
    public CommonReturnType(int code,Object data){
        this.code=code;
        this.data=data;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
