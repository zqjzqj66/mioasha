package com.imooc;

import com.imooc.dao.UserDoMapper;
import com.imooc.dataobject.UserDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@RestController
public class App 
{

    @Bean
    public ResourceUrlProvider mvcResourceUrlProvider(){
        return new ResourceUrlProvider();
    }

    @Autowired
    private UserDoMapper userDoMapper;

    @GetMapping("/hello")
    public String hello(){
        UserDo userDo = userDoMapper.selectByPrimaryKey(1);
        if(userDo==null)
            return "用户不存在";
        return "用户存在";

    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }
}
