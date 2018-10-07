package com.fly.sms;

import com.fly.domain.Account;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @author 游雄
 * @describe 存储短信令牌
 * @create 10:33 2018/10/7 0007
 */
public class SMSAbstractAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Object principal;
    private final String smsCode;

    private Account account;

    public SMSAbstractAuthenticationToken(Object principal, String smsCode) {
        super((Collection) null);
        this.principal = principal;
        this.smsCode = smsCode;
        this.setAuthenticated(false);
    }

    public SMSAbstractAuthenticationToken(Object principal,String smsCode ,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.smsCode = smsCode;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    public String getSmsCode() {
        return smsCode;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
