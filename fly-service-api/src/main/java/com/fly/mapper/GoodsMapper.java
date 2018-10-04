package com.fly.mapper;

import com.fly.domain.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author 游雄
 * @describe
 * @create 9:00 2018/10/3 0003
 */
@Mapper
public interface GoodsMapper {

    @Insert("insert into fly_goods(goods_name,num) values(#{goodsName},#{num})")
    void insert(Goods goods);

    @Update("update fly_goods set num = num - 1 where id=#{goodsId} and num > 0")
    Integer decreaseGoodsNum(Integer goodsId);

    @Select("select * from fly_goods where id = #{goodsId}")
    Goods findByGoodsId(Integer goodsId);
}
