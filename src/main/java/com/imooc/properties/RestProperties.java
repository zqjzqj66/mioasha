package com.imooc.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: RestProperties
 * @date 2019/7/13 20:49
 */
@Configuration
@ConfigurationProperties(prefix = RestProperties.REST_PREFIX)
public class RestProperties {

    public static  final String REST_PREFIX = "rest";

    private boolean authOpen = true;

    public boolean isAuthOpen() {
        return authOpen;
    }

    public void setAuthOpen(boolean authOpen) {
        this.authOpen = authOpen;
    }
}
