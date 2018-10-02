package com.fly.service;

import com.fly.domain.Client;
import org.springframework.security.oauth2.provider.ClientDetailsService;

/**
 * @author 游雄
 * @describe
 * @create 12:07 2018/10/2 0002
 */
public interface ClientService extends ClientDetailsService{
    Client findByClientId(String clientId);
}
