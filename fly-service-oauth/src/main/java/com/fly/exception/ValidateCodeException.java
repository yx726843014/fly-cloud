package com.fly.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 游雄
 * @describe
 * @create 9:43 2018/10/7 0007
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
