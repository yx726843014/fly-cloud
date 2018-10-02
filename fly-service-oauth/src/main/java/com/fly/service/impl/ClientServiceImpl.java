package com.fly.service.impl;

import com.fly.annotation.DynamicDataSource;
import com.fly.config.ds.DatabaseType;
import com.fly.domain.Client;
import com.fly.mapper.ClientMapper;
import com.fly.service.ClientService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author 游雄
 * @describe
 * @create 12:07 2018/10/2 0002
 */
@Service
@DynamicDataSource(type = DatabaseType.oauth)
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientMapper clientMapper;

    @Override
    public Client findByClientId(String clientId) {
        if(StringUtils.isBlank(clientId)){
            return null;
        }
        return clientMapper.findByClientId(clientId);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client client = clientMapper.findByClientId(clientId);
        return clientDetails(client);
    }


    private ClientDetails clientDetails(Client client){
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(client.getClientId());
        clientDetails.setScope(Arrays.asList("all"));
        clientDetails.setClientSecret(client.getSecret());
        clientDetails.setAuthorizedGrantTypes(Arrays.asList(client.getGrantType().split(",")));
        clientDetails.setRefreshTokenValiditySeconds(10000);
        clientDetails.setAccessTokenValiditySeconds(10000);
        return clientDetails;
    }
}
