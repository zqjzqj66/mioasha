package com.imooc.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: MyCheckValidator
 * @date 2019/6/24 10:00
 */
public class MyCheckValidator implements ConstraintValidator<MyCheckBlank,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value!=null && value.contains(" ")){
            String defaultConstraintMessageTemplate = context.getDefaultConstraintMessageTemplate();
            System.out.println("默认提示语"+defaultConstraintMessageTemplate );
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("3字符串").addConstraintViolation();
            return false;
        }

        return true;
    }
}
