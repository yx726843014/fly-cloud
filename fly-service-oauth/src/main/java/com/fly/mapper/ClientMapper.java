package com.fly.mapper;

import com.fly.domain.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 游雄
 * @describe
 * @create 12:06 2018/10/2 0002
 */
@Mapper
public interface ClientMapper {

    @Select("select * from fly_client where client_id = #{clientId}")
    Client findByClientId(@Param("clientId") String clientId);
}
