package com.fly.exception;

import com.fly.util.Result;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 游雄
 * @describe
 * @create 10:04 2018/10/3 0003
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object exceptionHandler(Exception e){
        e.printStackTrace();
        if(e instanceof OAuth2Exception){
            OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
            int errorCode = oAuth2Exception.getHttpErrorCode();
            String oAuth2ErrorCode = oAuth2Exception.getOAuth2ErrorCode();
            return Result.buildFailure(errorCode,"认证失败");
        }
        return Result.buildFailure(500,"服务繁忙");
    }
}
