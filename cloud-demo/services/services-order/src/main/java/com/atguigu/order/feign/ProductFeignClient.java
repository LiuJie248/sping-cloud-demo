package com.atguigu.order.feign;

import com.atguigu.order.feign.fallback.ProductFeignClientFailback;
import com.atguigu.product.bean.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/8
 */
@FeignClient(value = "services-product",fallback = ProductFeignClientFailback.class) // 发送请求
public interface ProductFeignClient {

    // 发送get请求去请求服务
    @GetMapping("/product/{id}")
    Product getProductById(@PathVariable("id") Long id);
}
