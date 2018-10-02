package com.fly.mapper;

import com.fly.domain.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 游雄
 * @describe
 * @create 14:23 2018/10/2 0002
 */
@Mapper
public interface AccountMapper {

    @Select("select * from fly_account where username = #{username}")
    Account findByUsername(String username);
}
