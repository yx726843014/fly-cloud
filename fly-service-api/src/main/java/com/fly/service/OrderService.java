package com.fly.service;

import com.fly.domain.Account;
import com.fly.domain.Goods; /**
 * @author 游雄
 * @describe
 * @create 9:00 2018/10/3 0003
 */
public interface OrderService {
    Integer addOrder(Goods goods, Account account);

}
