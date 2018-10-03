package com.fly.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 游雄
 * @describe
 * @create 8:57 2018/10/3 0003
 */
@Data
public class Order implements Serializable {

    private Integer id;

    private String orderNo;

    private String goodsName;

    private String goodsId;

    private Integer num;
}
