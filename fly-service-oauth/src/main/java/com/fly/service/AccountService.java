package com.fly.service;

import com.fly.domain.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author 游雄
 * @describe
 * @create 14:24 2018/10/2 0002
 */
public interface AccountService extends UserDetailsService {
    Account findByUsername(String admin);
}
