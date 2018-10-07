package com.fly.config;

import com.fly.exception.MyAccessDeniedHandler;
import com.fly.exception.MyAuthExceptionEntryPoint;
import com.fly.filter.ValidateCodeFilter;
import com.fly.sms.SMSSecurityConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 游雄
 * @describe
 * @create 14:43 2018/10/2 0002
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SMSSecurityConfigurer smsSecurityConfigurer;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter(), UsernamePasswordAuthenticationFilter.class).csrf().disable()
                .authorizeRequests()/*.antMatchers("/uaa/**").authenticated()*/
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/applications/**").permitAll()
                .antMatchers("/health/**").permitAll()
                .antMatchers("/validateCode/**").permitAll()
                .antMatchers("/smsCode/**").permitAll()
                .anyRequest().authenticated()
                .and().logout().permitAll()
                .and().formLogin().permitAll()
                .and().apply(smsSecurityConfigurer);
    }

    @Bean
    public ValidateCodeFilter validateCodeFilter(){
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter(authenticationFailureHandler);
        return validateCodeFilter;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(new MyAuthExceptionEntryPoint()).accessDeniedHandler(new MyAccessDeniedHandler());
    }


}
