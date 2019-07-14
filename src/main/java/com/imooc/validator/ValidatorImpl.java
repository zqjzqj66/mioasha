package com.imooc.validator;

import com.imooc.exception.ErrListEnum;
import com.imooc.exception.MyException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: ValidatorImpl
 * @date 2019/6/5 8:34
 */
@Component
public class ValidatorImpl implements InitializingBean{


    //@Autowired
    private  static Validator validator;

    public  static ValidationResult validate(Object obj){

        System.out.println("haha"+validator);
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> validate = validator.validate(obj);

        if(validate.size()>0){
            result.setHasErrors(true);
            validate.forEach(t->{
                String message = t.getMessage();
                result.getErrorMsgList().add(message);
            });
        }
        return result;
    }
    //把hibernate的validator作为javax.validation的validator的实现
    @Override
    public void afterPropertiesSet() throws Exception {
        validator= Validation.buildDefaultValidatorFactory().getValidator();
    }


    //用来处理前面cotroller的自动参数校验
    public static void dealValidation(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ValidationResult validationResult = new ValidationResult();
            validationResult.setHasErrors(true);

            bindingResult.getFieldErrors().stream().forEach(t->validationResult.getErrorMsgList().add(t.getDefaultMessage()));
            throw new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR,validationResult.getErrorMsg());
        }
    }
}
