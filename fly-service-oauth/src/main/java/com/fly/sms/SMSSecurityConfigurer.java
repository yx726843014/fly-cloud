package com.fly.sms;

import com.fly.config.MyAuthenticationFailureHandler;
import com.fly.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author 游雄
 * @describe  配置短信登录
 * @create 11:31 2018/10/7 0007
 */
@Component
public class SMSSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    @Autowired
    private SMSLoginSuccessHandler smsLoginSuccessHandler;

    @Autowired
    private AccountService accountService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        SMSAuthenticationFilter smsAuthenticationFilter = new SMSAuthenticationFilter();
        smsAuthenticationFilter.setAuthenticationManager(httpSecurity.getSharedObject(AuthenticationManager.class));
        smsAuthenticationFilter.setAuthenticationSuccessHandler(smsLoginSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        SMSAuthenticationProvider smsAuthenticationProvider = new SMSAuthenticationProvider(accountService,stringRedisTemplate);
        httpSecurity.authenticationProvider(smsAuthenticationProvider).addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
