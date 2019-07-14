package com.imooc.controller;

import com.imooc.properties.JwtProperties;
import com.imooc.response.CommonReturnType;
import com.imooc.util.JwtTokenUtil;
import com.imooc.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @RequestMapping(value = "/auth")
    public CommonReturnType createAuthenticationToken(@RequestParam("name") String name,
                                                       @RequestParam("password") String password) {
        final String randomKey = jwtTokenUtil.getRandomKey();
        final String token = jwtTokenUtil.generateToken(name, randomKey);
        //当使用auth来获取toekn的时候 也就是相当于登录的 所以要是要把数据像redis里面存放的
        RedisUtil.set(name, "");
        System.out.println("》》》》》》》》》"+token);
        return CommonReturnType.success("auto",token );
    }
}
