package com.atguigu.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.order.bean.Order;
import com.atguigu.order.properties.OrderProperties;
import com.atguigu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/7
 */
@RestController
@RequestMapping("/api/order")
//@RefreshScope
public class OrderController {

    @Value("${mykey}")
    String mykey;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderProperties orderProperties;

    @GetMapping("/getNacos")
    public String getNacos(){
        return mykey;
    }

    @GetMapping("/config")
    public String config(){
        return "order.timeout="+orderProperties.getTimeout()+";order.auto-confirm="+orderProperties.getAutoConfirm()
                +";order.db-url="+orderProperties.getDbUrl();
    }

    @GetMapping("/create")
    public Order createOrder(@RequestParam("productId") Long productId,
                             @RequestParam("userId") Long userId){
        return orderService.createOrder(productId,userId);
    }

    /**
     * 秒杀创建订单
     * @param productId
     * @param userId
     * @return
     */
    @GetMapping("/seckill")
    @SentinelResource(value = "seckill-order",fallback ="seckillFallback" )
    public Order seckill(@RequestParam("productId") Long productId,
                             @RequestParam("userId") Long userId){
        Order order =  orderService.createOrder(productId,userId);
        order.setId(Long.MAX_VALUE);
        return order;
    }
    public Order seckillFallback(Long productId,Long userId, BlockException blockException){
        System.out.println("seckillFallback...");
        Order order =  new Order();
        order.setUserId(userId);
        order.setId(productId);
        order.setAddress("异常信息"+blockException.getClass());
        return order;
    }

    @GetMapping("/writeDb")
    public String writeDb(){
        return "writeDb success...";
    }

    @GetMapping("/readDb")
    public String readDb(){
        return "readDb success...";
    }

}
