package com.atguigu.order.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/8
 */
@Data
//@ConfigurationProperties(prefix = "order")  // 获取所有前缀为order的属性,无需RefreshScope就能自动刷新nacos的配置
@Component
public class OrderProperties {

    String timeout;

    String autoConfirm;

    String dbUrl;

}
