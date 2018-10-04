package com.fly;

import com.fly.exception.MyAccessDeniedHandler;
import com.fly.exception.MyAuthExceptionEntryPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author 游雄
 * @describe
 * @create 9:14 2018/10/3 0003
 */
@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
public class ApiApplication extends ResourceServerConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class,args);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/order/**").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(new MyAuthExceptionEntryPoint()).accessDeniedHandler(new MyAccessDeniedHandler());
    }
}
