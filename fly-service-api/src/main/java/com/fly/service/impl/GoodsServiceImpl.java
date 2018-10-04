package com.fly.service.impl;

import com.fly.annotation.DynamicDataSource;
import com.fly.config.ds.DatabaseType;
import com.fly.domain.Account;
import com.fly.domain.Goods;
import com.fly.mapper.GoodsMapper;
import com.fly.service.AccountService;
import com.fly.service.GoodsService;
import com.fly.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

/**
 * @author 游雄
 * @describe
 * @create 9:01 2018/10/3 0003
 */
@Service
@DynamicDataSource(type = DatabaseType.goods)
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountService accountService;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public synchronized boolean buyGoods(Integer goodsId, Principal principal) {
        //查询用户信息
        Account account = accountService.findByUsername(principal.getName());
        if (account == null) {
            throw new RuntimeException("用户不存在");
        }
        Goods goods = this.findByGoodsId(goodsId);
        if (goods == null || goods.getNum() <= 0) {
            throw new RuntimeException("商品不足");
        }
        Integer num = decreaseGoodsNum(goods.getId());
        if (num <= 0) {
            throw new RuntimeException("购买失败");
        }
        //注意事项  当使用到对其他数据源进行增、删的时候要把对其他数据源操作放到最后面
        orderService.addOrder(goods, account);
        return true;
    }


    public Goods findByGoodsId(Integer goodsId) {
        return goodsMapper.findByGoodsId(goodsId);
    }

    @Override
    public Integer decreaseGoodsNum(Integer goodsId) {
        return goodsMapper.decreaseGoodsNum(goodsId);
    }

    @Override
    public void insertGoods(Goods goods) {
        goodsMapper.insert(goods);
    }
}
