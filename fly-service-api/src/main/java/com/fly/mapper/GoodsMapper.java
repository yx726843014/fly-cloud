package com.fly.mapper;

import com.fly.domain.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 游雄
 * @describe
 * @create 9:00 2018/10/3 0003
 */
@Mapper
public interface GoodsMapper {

    @Insert("insert into fly_goods(goods_name,num) values(#{goodsName},#{num})")
    void insert(Goods goods);
}
