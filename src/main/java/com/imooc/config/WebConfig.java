package com.imooc.config;

import com.imooc.filter.UserLoginFilter;
import com.imooc.properties.RestProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: WebConfig
 * @date 2019/6/9 12:23
 */
@Configuration
public class WebConfig{

    @Bean
    @ConditionalOnProperty(prefix = RestProperties.REST_PREFIX,name="auth-open",havingValue = "true",matchIfMissing = true)
    public UserLoginFilter userLoginFilter(){
        return new UserLoginFilter();
    }

}
