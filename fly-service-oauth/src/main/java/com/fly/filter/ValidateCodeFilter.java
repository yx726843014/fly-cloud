package com.fly.filter;

import com.fly.constant.RedisConstant;
import com.fly.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 游雄
 * @describe 图形验证码校验
 * @create 9:22 2018/10/7 0007
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

    public ValidateCodeFilter(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 图形验证码校验
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/oauth/token") && request.getMethod().toUpperCase().equals("POST") && !request.getParameter("grant_type").equals("refresh_token")
                && !request.getParameter("grant_type").equals("client_credentials")) {
            try {
                validateCode(request);
            } catch (ValidateCodeException e) {
                e.printStackTrace();
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(HttpServletRequest request) {
        String validateCode = request.getParameter("validateCode");
        String username = request.getParameter("username");
        if (StringUtils.isBlank(validateCode) || StringUtils.isBlank(username)) {
            throw new ValidateCodeException("请输入用户或验证码");
        }
        String redisValidateCode = stringRedisTemplate.opsForValue().get(RedisConstant.VALIDATE_CODE + ":" + username);
        if (StringUtils.isNotBlank(redisValidateCode)) {
            stringRedisTemplate.delete(RedisConstant.VALIDATE_CODE + ":" + username);
        }
        if (!validateCode.equals(redisValidateCode)) {
            throw new ValidateCodeException("验证码错误");
        }
    }
}
