package com.fly.service.impl;

import com.fly.annotation.DynamicDataSource;
import com.fly.config.ds.DatabaseType;
import com.fly.domain.Account;
import com.fly.mapper.AccountMapper;
import com.fly.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        Account account = accountMapper.findByUsername(username);
        if(account == null){
            throw new RuntimeException("用户不存在");
        }
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(account.getRole()));
        User user = new User(account.getUsername(),passwordEncoder.encode(account.getPassword()),list);
        return user;
    }

    @Override
    public Account findByUsername(String admin) {
        return accountMapper.findByUsername(admin);
    }
}
