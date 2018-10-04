package com.fly.web;

import com.fly.domain.Account;
import com.fly.service.AccountService;
import com.fly.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author 游雄
 * @describe
 * @create 15:20 2018/10/2 0002
 */
@RestController
public class AccountController {


    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "user",produces = "application/json")
    public Object user(Principal principal){
        //Account account = accountService.findByUsername("admin");
        return principal;
    }

}
