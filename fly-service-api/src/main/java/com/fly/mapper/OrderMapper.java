package com.fly.mapper;

import com.fly.domain.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 游雄
 * @describe
 * @create 9:00 2018/10/3 0003
 */
@Mapper
public interface OrderMapper {

    @Insert("insert into fly_order(order_no,goods_id,goods_name,account_id,num) values(#{orderNo}," +
            "#{goodsId},#{goodsName},#{accountId},#{num})")
    Integer insert(Order order);
}
