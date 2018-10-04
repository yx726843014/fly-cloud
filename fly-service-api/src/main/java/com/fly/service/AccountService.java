package com.fly.service;

import com.fly.domain.Account;
import org.apache.ibatis.annotations.Select;

/**
 * @author 游雄
 * @describe
 * @create 9:54 2018/10/4 0004
 */
public interface AccountService {

    Account findByUsername(String name);
}
