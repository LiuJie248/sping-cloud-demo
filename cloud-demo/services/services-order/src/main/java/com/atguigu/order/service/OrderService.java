package com.atguigu.order.service;


import com.atguigu.order.bean.Order;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/7
 */
public interface OrderService {

    Order createOrder(Long productId, Long userId);
}
