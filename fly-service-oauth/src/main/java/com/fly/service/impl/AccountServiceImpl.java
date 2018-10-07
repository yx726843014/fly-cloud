package com.fly.service.impl;

import com.fly.annotation.DynamicDataSource;
import com.fly.config.ds.DatabaseType;
import com.fly.domain.Account;
import com.fly.mapper.AccountMapper;
import com.fly.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 游雄
 * @describe
 * @create 14:24 2018/10/2 0002
 */
@Service
@DynamicDataSource(type = DatabaseType.oauth)
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account accountName = accountMapper.findByUsername(username);
        Account accountMobile = null;
        if (accountName == null) {
            accountMobile = accountMapper.findByMobile(username);
            if (accountMobile == null) {
                throw new RuntimeException("用户不存在");
            }
        }
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(accountName == null?accountMobile.getRole():accountName.getRole()));
        User user = new User(accountName == null?accountMobile.getMobile():accountName.getUsername()
                , passwordEncoder.encode(accountName == null?accountMobile.getPassword():accountName.getPassword()), list);
        return user;
    }

    @Override
    public Account findByUsername(String admin) {
        return accountMapper.findByUsername(admin);
    }

    @Override
    public Account findByMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return null;
        }
        return accountMapper.findByMobile(mobile);
    }
}
