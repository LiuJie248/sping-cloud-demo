package com.atguigu.order;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/3
 */
@SpringBootApplication
@EnableFeignClients // 开启远程调用
@MapperScan("com.atguigu.order.mapper")
public class OrderMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderMainApplication.class,args);
    }

    // 1 项目启动监听配置文件
    // 2 发生变化后拿到变化值
    // 3 发送邮件
    @Bean
    ApplicationRunner applicationRunner(NacosConfigManager nacosConfigManager){
        return args->{
            System.out.println("========");
            ConfigService configService = nacosConfigManager.getConfigService();
            configService.addListener("services-order.properties", "DEFAULT_GROUP", new Listener() {
                @Override
                public Executor getExecutor() {
                    // 固定四个线程
                    return Executors.newFixedThreadPool(4);
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
System.out.println("变化的配置信息："+configInfo);
System.out.println("发送邮件。。。");
                }
            });
        };
    }


}
