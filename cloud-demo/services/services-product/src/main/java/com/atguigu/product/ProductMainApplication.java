package com.atguigu.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/4
 */
@EnableDiscoveryClient // 开启服务发现
@SpringBootApplication
public class ProductMainApplication {

    private static final Logger logger = LoggerFactory.getLogger(ProductMainApplication.class);

     public static void main(String[] args) {

         try {
             SpringApplication.run(ProductMainApplication.class,args);
             logger.info("ProductMainApplication Startup successful!");
         }catch (Exception e) {
             logger.error("ProductMainApplication Startup abnormal", e); // 强制打印异常堆栈
         }
    }
}
