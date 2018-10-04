package com.fly.service.impl;

import com.fly.annotation.DynamicDataSource;
import com.fly.config.ds.DatabaseType;
import com.fly.domain.Account;
import com.fly.mapper.AccountMapper;
import com.fly.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 游雄
 * @describe
 * @create 9:54 2018/10/4 0004
 */
@Service
@DynamicDataSource(type = DatabaseType.oauth)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Account findByUsername(String name) {
        return accountMapper.findByUsername(name);
    }
}
