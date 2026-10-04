package com.atguigu.product.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/7
 */
@Data
public class Product {

    private Long id;
    private BigDecimal price;
    private String productName;
    private int num;
}
