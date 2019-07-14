package com.imooc.service.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: UserModel
 * @date 2019/6/2 15:11
 */
@Data
public class UserModel {
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotNull(message = "年龄不能不填写")
    @Min(value = 0,message = "年龄必须大于0")
    @Max(value=150,message = "年龄必须小于150岁")
    private Integer age;

    @NotNull(message = "性别不能不填写")
    private Byte gender;

    @NotBlank(message = "手机号不能为空")
    private String telphone;

    private String registerMode;

    private String thirdPartyId;

    @NotBlank(message = "密码不能为空")
    private String password;

}
