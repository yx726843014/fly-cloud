package com.fly.web;

import com.fly.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author 游雄
 * @describe
 * @create 9:15 2018/10/3 0003
 */
@RestController
@RequestMapping(value = "order")
public class OrderController {



    @RequestMapping(value = "add")
    public Object add(Principal principal){
        return Result.buildSuccess(principal);
    }
}
