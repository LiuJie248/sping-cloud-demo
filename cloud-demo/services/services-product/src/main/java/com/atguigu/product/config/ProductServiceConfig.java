package com.atguigu.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/7
 */
@Configuration
public class ProductServiceConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
