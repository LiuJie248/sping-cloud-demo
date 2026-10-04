package com.atguigu.product.controller;

import com.atguigu.product.bean.Product;
import com.atguigu.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/7
 */
@RestController
//@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;
    /**
     * 查询商品
     * @param productId
     */
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Long productId,
                              HttpServletRequest httpServletRequest){
        String header = httpServletRequest.getHeader("X-Token");
        System.out.println("hello......token=["+header+"]");
        return productService.getProductById(productId);
    }
}
