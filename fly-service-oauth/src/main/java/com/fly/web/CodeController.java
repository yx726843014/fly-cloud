package com.fly.web;

import com.fly.constant.RedisConstant;
import com.fly.domain.Account;
import com.fly.service.AccountService;
import com.fly.util.Result;
import com.fly.utils.CodeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author 游雄
 * @describe
 * @create 9:54 2018/10/7 0007
 */
@RestController
public class CodeController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/validateCode/{account}")
    public Object getValidateCode(@PathVariable("account") String account){
        if(StringUtils.isBlank(account)){
            return Result.buildFailure("参数不完整");
        }
        Account user = accountService.findByUsername(account);
        if(user == null){
            return Result.buildFailure("用户不存在");
        }
        String randomCode = CodeUtil.getRandomCode();
        stringRedisTemplate.opsForValue().set(RedisConstant.VALIDATE_CODE+":"+user.getUsername(),randomCode,60, TimeUnit.SECONDS);
        return Result.buildSuccess(randomCode);
    }

    @RequestMapping(value = "/smsCode/{mobile}")
    public Object getSMSCode(@PathVariable("mobile") String mobile){
        if(StringUtils.isBlank(mobile)){
            return Result.buildFailure("参数不完整");
        }
        Account user = accountService.findByMobile(mobile);
        if(user == null){
            return Result.buildFailure("账号不存在");
        }
        String repeatCode = stringRedisTemplate.opsForValue().get(RedisConstant.SMS_CODE + ":" + user.getMobile());
        if(StringUtils.isNotBlank(repeatCode)){
            return Result.buildFailure("请勿重复发送短信验证码");
        }
        String limitCount = stringRedisTemplate.opsForValue().get(RedisConstant.SMS_LIMIT + ":" + user.getMobile());
        if(StringUtils.isNotBlank(limitCount) && Integer.valueOf(limitCount) >=5 ){
            return Result.buildFailure("今日短信发送达到上限");
        }
        stringRedisTemplate.opsForValue().increment(RedisConstant.SMS_LIMIT + ":" + user.getMobile(),0);
        stringRedisTemplate.expire(RedisConstant.SMS_LIMIT + ":" + user.getMobile(),3600 * 24,TimeUnit.SECONDS);
        String randomCode = CodeUtil.getRandomCode();
        stringRedisTemplate.opsForValue().set(RedisConstant.SMS_CODE+":"+user.getMobile(),randomCode,60, TimeUnit.SECONDS);
        return Result.buildSuccess(randomCode);
    }

}
