package com.atguigu.order.interceptor;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/10
 */
@Component
public class XTokenRequestInterceptor implements RequestInterceptor{

    /**
     * Called for every request. Add data using methods on the supplied {@link RequestTemplate}.
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        System.out.println("XTokenRequestInterceptor......");
        // 请求header增加X-Token，发给商品服务
        template.header("X-Token", UUID.randomUUID().toString());
    }

}
