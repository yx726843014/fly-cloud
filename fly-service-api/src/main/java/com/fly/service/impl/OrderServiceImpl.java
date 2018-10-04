package com.fly.service.impl;

import com.fly.annotation.DynamicDataSource;
import com.fly.config.ds.DatabaseType;
import com.fly.domain.Account;
import com.fly.domain.Goods;
import com.fly.domain.Order;
import com.fly.mapper.OrderMapper;
import com.fly.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author 游雄
 * @describe
 * @create 9:13 2018/10/3 0003
 */
@Service
@DynamicDataSource(type = DatabaseType.order)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer addOrder(Goods goods, Account account) {
        if(goods == null){
            throw new RuntimeException("商品不存在");
        }
        Order order = new Order();
        order.setGoodsId(goods.getId());
        order.setGoodsName(goods.getGoodsName());
        order.setNum(1);
        order.setAccountId(account.getId());
        order.setOrderNo(UUID.randomUUID().toString().replaceAll("-",""));
        Integer insert = orderMapper.insert(order);
        return insert;
    }
}
