package com.fly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 游雄
 * @describe
 * @create 12:05 2018/10/2 0002
 */
@SpringBootApplication
@EnableEurekaClient
public class OauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class,args);
    }
}
