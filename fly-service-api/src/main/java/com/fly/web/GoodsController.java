package com.fly.web;

import com.fly.domain.Goods;
import com.fly.service.GoodsService;
import com.fly.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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

}
