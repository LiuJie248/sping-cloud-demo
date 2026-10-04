package com.atguigu.order.config;

import feign.Logger;
import feign.RetryableException;
import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/7
 */
@Configuration
public class OrderConfig {

    //@Bean
    Retryer retryer(){
        return new Retryer.Default();
    }

    @Bean
    @LoadBalanced // 注解式负载均衡调用服务
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL; // feign日志全记录组件
    }
}
