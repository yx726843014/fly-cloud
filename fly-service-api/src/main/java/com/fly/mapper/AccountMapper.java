package com.fly.mapper;

import com.fly.domain.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 游雄
 * @describe
 * @create 9:48 2018/10/4 0004
 */
@Mapper
public interface AccountMapper {

    @Select("select * from fly_account where username = #{name}")
    Account findByUsername(String name);
}
