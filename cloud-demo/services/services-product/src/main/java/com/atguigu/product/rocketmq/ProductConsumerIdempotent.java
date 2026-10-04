package com.atguigu.product.rocketmq;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/19
 */
@Component
@RocketMQMessageListener(consumerGroup = "md-consumerGroup",topic = "topicTransact",
        selectorType = SelectorType.TAG,selectorExpression = "createOrder || shipped"  //只消费Tag为createOrder或shipped
         )
public class ProductConsumerIdempotent implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        System.out.println("收到订单消息：" + message);
        // 获取生产者设置的业务唯一Key
        String orderId = message.getKeys();
        // 基于 orderId 进行幂等判断（如查询数据库是否已处理）
        // 获取消息体
        String body = new String( message.getBody() );
    }

}
