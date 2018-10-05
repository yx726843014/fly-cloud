package com.fly.web;

import com.fly.domain.Goods;
import com.fly.service.GoodsService;
import com.fly.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 游雄
 * @describe
 * @create 9:18 2018/10/3 0003
 */
@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "add")
    public Object addGoods(Principal principal){
        Goods goods = new Goods();
        goods.setGoodsName("IPhone 100");
        goods.setNum(10);
        goodsService.insertGoods(goods);
        return Result.buildSuccess(principal);
    }

    @GetMapping(value = "/buy/{goodsId}")
    public Object buyGoods(OAuth2Authentication principal, @PathVariable("goodsId") Integer goodsId){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal.getUserAuthentication();
        Map<Object,Object> details = (Map<Object, Object>) usernamePasswordAuthenticationToken.getDetails();
        Object userAuthentication = details.get("userAuthentication");
        if(userAuthentication == null){
            return Result.buildFailure("未登录");
        }
        boolean flag = goodsService.buyGoods(goodsId,principal);
        if(flag){
            return Result.buildSuccess("购买成功");
        }
        return Result.buildFailure("购买失败");
    }

}
