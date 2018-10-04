package com.fly.service;

import com.fly.domain.Goods;

import java.security.Principal; /**
 * @author 游雄
 * @describe
 * @create 9:00 2018/10/3 0003
 */
public interface GoodsService {
    void insertGoods(Goods goods);

    boolean buyGoods(Integer goodsId, Principal principal);

    Goods findByGoodsId(Integer goodsId);

    Integer decreaseGoodsNum(Integer goodsId);
}
