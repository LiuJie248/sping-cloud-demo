package com.atguigu.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/8
 */
@SpringBootTest
public class LoadBalancerTest {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    void test(){
        // 负责均衡的选择一个地址
        ServiceInstance serviceInstance = loadBalancerClient.choose("services-product");
    }
}
