package com.fly.config;

import com.fly.exception.MyAccessDeniedHandler;
import com.fly.exception.MyAuthExceptionEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 游雄
 * @describe
 * @create 14:43 2018/10/2 0002
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()/*.antMatchers("/uaa/**").authenticated()*/
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/applications/**").permitAll()
                .antMatchers("/health/**").permitAll()
                .anyRequest().authenticated()
                .and().logout().permitAll()
                .and().formLogin().permitAll();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(new MyAuthExceptionEntryPoint()).accessDeniedHandler(new MyAccessDeniedHandler());
    }
}
