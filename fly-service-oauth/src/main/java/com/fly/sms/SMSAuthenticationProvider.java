package com.fly.sms;

import com.fly.constant.RedisConstant;
import com.fly.domain.Account;
import com.fly.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 游雄
 * @describe 进行处理短信账号验证
 * @create 10:46 2018/10/7 0007
 */
public class SMSAuthenticationProvider implements AuthenticationProvider {

    private AccountService accountService;

    private StringRedisTemplate stringRedisTemplate;

    public SMSAuthenticationProvider(AccountService accountService, StringRedisTemplate stringRedisTemplate) {
        this.accountService = accountService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SMSAbstractAuthenticationToken sms = (SMSAbstractAuthenticationToken) authentication;
        String mobile = (String) sms.getPrincipal();//拿到手机号码
        Account account = accountService.findByMobile(mobile);
        if (account == null) {
            throw new InternalAuthenticationServiceException("用户不存在");
        }
        String redisSMSCode = stringRedisTemplate.opsForValue().get(RedisConstant.SMS_CODE + ":" + account.getMobile());
        if(StringUtils.isBlank(redisSMSCode)){
            throw new InternalAuthenticationServiceException("请先获取短信验证码");
        }
        if(!sms.getSmsCode().equals(redisSMSCode)){
            throw new InternalAuthenticationServiceException("验证码错误");
        }
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(account.getRole()));
        User user = new User(account.getMobile(), sms.getSmsCode(), list);
        SMSAbstractAuthenticationToken sms2 = new SMSAbstractAuthenticationToken(user,sms.getSmsCode() ,user.getAuthorities());
        sms2.setDetails(sms.getDetails());
        return sms2;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SMSAbstractAuthenticationToken.class.isAssignableFrom(aClass);
    }


}
