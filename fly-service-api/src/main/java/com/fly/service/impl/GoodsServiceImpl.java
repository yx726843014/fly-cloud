package com.fly.service.impl;

import com.fly.annotation.DynamicDataSource;
import com.fly.config.ds.DatabaseType;
import com.fly.domain.Goods;
import com.fly.mapper.GoodsMapper;
import com.fly.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void insertGoods(Goods goods) {
        goodsMapper.insert(goods);
    }
}
