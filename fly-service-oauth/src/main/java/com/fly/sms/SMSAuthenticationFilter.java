package com.fly.sms;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 游雄
 * @describe 过滤器获取请求内容
 * @create 10:37 2018/10/7 0007
 */
public class SMSAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "username";
    public static final String SPRING_SECURITY_FORM_SMS_CODE_KEY = "smsCode";
    private String mobileParameter = "mobile";
    private String smsCodeParameter = "smsCode";
    private boolean postOnly = true;

    protected SMSAuthenticationFilter() {
        super(new AntPathRequestMatcher("/mobile/token", HttpMethod.POST.name()));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String mobile = this.obtainMobile(request);
            String smsCode = this.obtainSMSCode(request);
            if (mobile == null) {
                mobile = "";
            }
            if (smsCode == null) {
                smsCode = "";
            }
            mobile = mobile.trim();
            smsCode = smsCode.trim();
            SMSAbstractAuthenticationToken authRequest = new SMSAbstractAuthenticationToken(mobile, smsCode);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }


    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(this.mobileParameter);
    }

    protected String obtainSMSCode(HttpServletRequest request) {
        return request.getParameter(this.smsCodeParameter);
    }

    protected void setDetails(HttpServletRequest request, SMSAbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return this.mobileParameter;
    }

}
