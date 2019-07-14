package com.imooc.validator.test;

import com.imooc.validator.MyCheckBlank;
import com.imooc.validator.inter.Insert;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: Student
 * @date 2019/6/24 8:45
 */
@Data
public class Student {

    //@MyCheckBlank(message = "haha")
    @NotBlank(message = "姓名不能为空",groups = {Insert.class})
    private String name;

    @NotNull(message = "年龄不能为空")
    @Min(value = 10,message = "年龄要大于十岁")
    private String age;

    @NotNull(message = "分数不能为空")
    @Min(value = 0, message = "分数要大于0")
    private Integer score;

}
