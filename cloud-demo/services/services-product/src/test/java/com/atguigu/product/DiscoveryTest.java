package com.atguigu.product;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/4
 */
@SpringBootTest
public class DiscoveryTest {

    @Autowired
    DiscoveryClient discoveryClient;

    //@Autowired
    //NacosServiceDiscovery nacosServiceDiscovery;

    @Test
    void discoveryClientTest(){
        List<String> res = discoveryClient.getServices();
        for( String service:res){
            System.out.println("Service="+service);
            // 获取ip和端口
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance :instances){
                System.out.println("ip="+instance.getHost()+"，端口="+instance.getPort());
            }
        }
    }
}
