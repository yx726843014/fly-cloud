package com.fly.config;

import com.fly.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 游雄
 * @describe
 * @create 17:53 2018/10/2 0002
 */
@FrameworkEndpoint
public class TokenRevokeEndpoint {

    @Autowired
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices tokenServices;

    @DeleteMapping("/oauth/token")
    @ResponseBody
    public Result<String> deleteAccessToken(@RequestParam("access_token") String accessToken) {
        if (tokenServices.revokeToken(accessToken)) {
            return Result.buildSuccess("注销成功");
        }
        return Result.buildFailure("注销失败");
    }
}
