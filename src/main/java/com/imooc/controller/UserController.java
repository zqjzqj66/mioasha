package com.imooc.controller;

import com.imooc.controller.viewobject.UserVO;
import com.imooc.exception.ErrListEnum;
import com.imooc.exception.MyException;
import com.imooc.response.CommonReturnType;
import com.imooc.service.UserService;
import com.imooc.service.model.UserModel;
import com.imooc.util.JwtTokenUtil;
import com.imooc.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Random;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: UserController
 * @date 2019/6/2 14:58
 */
@RestController
@RequestMapping("/user")//origins= {"http://localhost:63342"},
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @PostMapping("/login")
    public  CommonReturnType login(@RequestParam("telphone")String telphone,
                                   @RequestParam("password")String password){
        //入参校验  周启江  这一步一定要时刻的保持住  以后还是多使用一些这样的 工具包
        if(StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password))
            throw new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR,"用户名和密码不能为空");

        UserModel userModel = userService.validateLogin(telphone, encodeByMD5(password));
        if (userModel==null)
            throw new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR,"用户名或者密码错误");
        /*httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);*/

        final String randomKey = jwtTokenUtil.getRandomKey();
        final String token = jwtTokenUtil.generateToken(userModel.getId().toString(), randomKey);
        //登录成功之后 把以用户id为key “” 为value存放到redis里面的
        RedisUtil.set(userModel.getId().toString(), "");
        System.out.println(">>>>>>>>>>>>>>>>"+token);
        return CommonReturnType.success();
    }

    @PostMapping("/register")
    public CommonReturnType register(@RequestParam("telphone")String telphone,
                                     @RequestParam("otpCode")String otpCode,
                                     @RequestParam("name")String name,
                                     @RequestParam("gender")Byte gender,
                                     @RequestParam("age")Integer age,
                                     @RequestParam("password")String password){
        String otp=(String)httpServletRequest.getSession().getAttribute(telphone);

        //如果验证码不对
        if(!StringUtils.equals(otp,otpCode )){
            throw new MyException(ErrListEnum.PARAMETER_VALIDATION_ERROR,"验证码不对");
        }
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setRegisterMode("byphone");
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setTelphone(telphone);

        userModel.setPassword(encodeByMD5(password));

        userService.register(userModel);
        final String randomKey = jwtTokenUtil.getRandomKey();
        final String token = jwtTokenUtil.generateToken(userModel.getId().toString(), randomKey);
        return CommonReturnType.success();
    }



    //密码加密
    public static String encodeByMD5(String str) {
        MessageDigest md5 = null;
        String encode;
        try {
            md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder=new BASE64Encoder();
            encode = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException  e) {
            throw new MyException(ErrListEnum.UNKNOWN_ERROR,"密码加密错误");
        }
        return encode;
    }


    @GetMapping("/getOtp")
    public CommonReturnType getOtp(@RequestParam("telphone")String telpone){

        //首先通过一定的规则来生成otp验证码
        Random random = new Random();
        //生成1000到9999之间的验证码
        String randomCode = String.valueOf(random.nextInt(8999)+1000);


        //将otp验证码和手机号绑定 这里使用redis是最合适的 redis好似kv的形式 并且包含有过期时间
        //但是这里之后我会去做的
        //TODO
        //这里使用httpservletRequest来存储数据
        httpServletRequest.getSession().setAttribute(telpone,randomCode);

        System.out.println(httpServletRequest.getSession().getAttribute(telpone));
        //通过短信发送otp验证码
        System.out.println(telpone+" "+randomCode);

        return CommonReturnType.success();
    }


    @GetMapping("/getUserById")
    public CommonReturnType getUserById(@RequestParam("id")Integer id){
        UserModel userById = userService.getUserById(id);

        UserVO userVO = convertFromModel(userById);
        if (!Objects.isNull(userVO))
            return CommonReturnType.success(userVO);
        throw new MyException(ErrListEnum.USER_NO_EXIST);
    }



    private UserVO convertFromModel(UserModel userModel){
        if (Objects.isNull(userModel))
            return null;

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO );
        return userVO;
    }



}
