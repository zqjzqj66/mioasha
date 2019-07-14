package com.imooc.validator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: ValidationResult
 * @date 2019/6/5 8:28
 */
@Data
public class ValidationResult {

    //是否有错
    private boolean hasErrors;

    //使用map来存储错误的信息
    private List<String> errorMsgList;

    public ValidationResult(){
        hasErrors=false;
        errorMsgList=new ArrayList<>();
    }

    //将错误信息组合成为一条sql语句返回
    public String getErrorMsg(){
        String join = StringUtils.join(errorMsgList.toArray(), ",");
        return join;
    }

}
