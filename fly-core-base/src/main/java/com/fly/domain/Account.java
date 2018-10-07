package com.fly.domain;

import lombok.Data;

/**
 * @author 游雄
 * @describe
 * @create 12:04 2018/10/2 0002
 */
@Data
public class Account {

    private Integer id;

    private String username;

    private String password;

    private String role;

    private String mobile;

}
