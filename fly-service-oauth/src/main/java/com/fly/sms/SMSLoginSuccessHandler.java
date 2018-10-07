package com.fly.sms;

import com.alibaba.fastjson.JSONObject;
import com.fly.constant.RedisConstant;
import com.fly.service.AccountService;
import com.fly.service.ClientService;
import com.fly.service.impl.MyTokenServices;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Set;

/**
 * @author 游雄
 * @describe 用户处理成功之后需要对client进行校验
 * @create 11:07 2018/10/7 0007
 */
@Component
@Slf4j
public class SMSLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ClientService clientService;


    @Autowired
    private AuthorizationServerTokenServices myTokenServices;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String BASIC = "Basic";


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith(BASIC)) {
            throw new UnapprovedClientAuthenticationException("请求头中client信息为空");
        }

        try {
            String[] tokens = decodeHeader(header);
            assert tokens.length == 2;
            String clientId = tokens[0];

            ClientDetails clientDetails = clientService.loadClientByClientId(clientId);

            //校验secret
            if (!clientDetails.getClientSecret().equals(tokens[1])) {
                throw new InvalidClientException("secret检验失败");
            }
            //校验grant_type
            String grant_type = request.getParameter("grant_type");
            if (!grant_type.equals("mobile")) {
                throw new InvalidClientException("grant_type错误");
            }
            Set<String> grantTypes = clientDetails.getAuthorizedGrantTypes();
            boolean flag = true;
            for (String grantType : grantTypes) {
                if (grantType.equals("mobile")) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                throw new InvalidClientException("client不支持mobile模式");
            }
            TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), "mobile");

            //校验scope
            new DefaultOAuth2RequestValidator().validateScope(tokenRequest, clientDetails);
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
            OAuth2AccessToken oAuth2AccessToken = myTokenServices.createAccessToken(oAuth2Authentication);
            log.info("获取token 成功：{}", oAuth2AccessToken.getValue());
            String mobile = request.getParameter("mobile");
            stringRedisTemplate.delete(RedisConstant.SMS_CODE + ":" + mobile);
            stringRedisTemplate.delete(RedisConstant.SMS_LIMIT + ":" + mobile);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            PrintWriter printWriter = response.getWriter();
            printWriter.print(JSONObject.toJSON(oAuth2AccessToken));
        } catch (IOException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }
    }

    private String[] decodeHeader(String header) {
        if (StringUtils.isBlank(header)) {
            return null;
        }
        byte[] headerByte = new byte[0];
        try {
            headerByte = header.substring(6).getBytes("utf-8");
            byte[] decode = Base64.decode(headerByte);
            String stringHeader = new String(decode, "utf-8");
            return stringHeader.split(":");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
