package com.fly.service.impl;

import com.fly.annotation.DynamicDataSource;
import com.fly.config.ds.DatabaseType;
import com.fly.mapper.OrderMapper;
import com.fly.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
