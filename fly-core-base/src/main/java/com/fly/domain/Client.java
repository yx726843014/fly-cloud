package com.fly.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 游雄
 * @describe
 * @create 12:00 2018/10/2 0002
 */
@Data
public class Client implements Serializable{

    private Integer id;

    private String clientId;

    private String secret;

    private String grantType;
}
