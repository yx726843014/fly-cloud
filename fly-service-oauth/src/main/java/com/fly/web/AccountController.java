package com.fly.web;

import com.fly.domain.Account;
import com.fly.service.AccountService;
import com.fly.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @author 游雄
 * @describe
 * @create 15:20 2018/10/2 0002
 */
@RestController
public class AccountController {


    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "user", produces = "application/json")
    public Object user(OAuth2Authentication principal) {
        OAuth2Request oAuth2Request = principal.getOAuth2Request();
        String grantType = oAuth2Request.getGrantType();
        if (grantType.equals("password")) {
            Account account = accountService.findByUsername(principal.getName());
            if(account != null){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal.getUserAuthentication();
                Map<Object, Object> details = (Map<Object, Object>) usernamePasswordAuthenticationToken.getDetails();
                details.put("account",account);
               // principal.setDetails(details);
            }
        }
        return principal;
    }

}
