package com.fly.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 游雄
 * @describe
 * @create 8:58 2018/10/3 0003
 */
@Data
public class Goods implements Serializable {

    private Integer id;

    private String goodsName;

    private Integer num;
}
