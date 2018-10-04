package com.fly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author 游雄
 * @describe
 * @create 10:39 2018/10/3 0003
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZuulApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class,args);
    }


    /*@Bean
    public MyFilter myFilter(){
        return new MyFilter();
    }*/


}
