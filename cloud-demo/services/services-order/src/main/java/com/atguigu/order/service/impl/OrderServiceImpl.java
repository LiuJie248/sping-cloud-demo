package com.atguigu.order.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.order.bean.Order;
import com.atguigu.order.feign.ProductFeignClient;
import com.atguigu.order.service.OrderService;
import com.atguigu.product.bean.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/7
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    ProductFeignClient productFeignClient;

    /**
     * @param productId
     * @param userId
     * @return
     */
    @Override
    @SentinelResource(value = "createOrder",blockHandler = "createOrderFallback")
    public Order createOrder(Long productId, Long userId) {
        //Product product = getProductFromRemoteWithLoadBalancerAnnotation(productId);
        Product product = productFeignClient.getProductById(productId); // 使用Feign远程调用

        Order order=new Order();
        order.setId(1l);
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId);
        order.setNickName("zhangsan");
        order.setAddress("上硅谷");
        order.setProductList(Arrays.asList(product));
        return order;
    }

    /**
     * 兜底回调
     * @param productId
     * @param userId
     * @param blockException
     * @return
     */
    public Order createOrderFallback(Long productId, Long userId, BlockException blockException){
        Order order=new Order();
        order.setId(0l);
        order.setTotalAmount(new BigDecimal(0));
        order.setUserId(userId);
        order.setNickName("未知用户");
        order.setAddress("异常信息："+blockException.getClass());
        return order;
    }

    /**
     * 请求商品服务
     * @param productId
     * @return
     */
    private Product getProductFromRemote(Long productId ){
// 获取商品服务
        List<ServiceInstance> instances = discoveryClient.getInstances("services-product");
        ServiceInstance instance = instances.get(0);
        String url = "http://" + instance.getHost() + ":" + instance.getPort()+"/product/"+productId;
        log.info("远程请求商品路径{}",url);
        // 发请求
        Product product = restTemplate.getForObject(url,Product.class);
        return product;
    }

    /**
     * 进阶2，负载均衡的请求服务
     * @param productId
     * @return
     */
    private Product getProductFromRemoteWithLoadBalancer(Long productId ){
// 获取商品服务
        ServiceInstance instance = loadBalancerClient.choose("services-product");
        String url = "http://" + instance.getHost() + ":" + instance.getPort()+"/product/"+productId;
        log.info("远程请求商品路径{}",url);
        // 发请求
        Product product = restTemplate.getForObject(url,Product.class);
        return product;
    }

    /**
     * 进阶3，注解式负载均衡请求服务
     * @param productId
     * @return
     */
    private Product getProductFromRemoteWithLoadBalancerAnnotation(Long productId ){
// 获取商品服务,发请求
        String url="http://services-product/product/"+productId;
        Product product = restTemplate.getForObject(url,Product.class);
        return product;
    }

}
