package indi.mofan.order.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/14
 */
@Configuration
public class OrderConfig {

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL; // feign日志全记录组件
    }
    
}
