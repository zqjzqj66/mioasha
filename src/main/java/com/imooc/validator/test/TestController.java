package com.imooc.validator.test;

import com.imooc.exception.ErrListEnum;
import com.imooc.exception.MyException;
import com.imooc.validator.ValidationResult;
import com.imooc.validator.ValidatorImpl;
import com.imooc.validator.inter.Insert;
import com.imooc.validator.inter.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: TestController
 * @date 2019/6/24 8:49
 */
@RestController
public class TestController {

    @Autowired
    private ValidatorImpl validator;

    @GetMapping("/test")
    public String test(@Validated({Insert.class})Student student, BindingResult bindingResult){
        System.out.println(student);
//        bindingResult.getFieldErrors().stream().forEach(t->{
//            System.out.println(t.getField()+"    "+t.getDefaultMessage());
//        });

       ValidatorImpl.dealValidation(bindingResult);
        /*
        ValidationResult validate = validator.validate(student);
        if (validate.isHasErrors()){
            validate.getErrorMsgList().stream().forEach(System.out::println);
        }*/
        return "hello";
    }

}
