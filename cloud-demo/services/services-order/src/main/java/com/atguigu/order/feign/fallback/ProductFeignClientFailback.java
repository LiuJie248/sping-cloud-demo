package com.atguigu.order.feign.fallback;

import com.atguigu.order.feign.ProductFeignClient;
import com.atguigu.product.bean.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/10
 */
@Component
public class ProductFeignClientFailback implements ProductFeignClient {
    /**
     * @param id
     * @return
     */
    @Override
    public Product getProductById(Long id) {
        System.out.println("兜底回调，不一定会被调用，需要sentinel。 比如商品服务挂了，在接口请求不通的下会被调用");
        Product product = new Product();
        product.setId(id);
        product.setPrice(new BigDecimal(0));
        product.setProductName("未知商品");
        product.setNum(0);
        return product;
    }
}
