package com.atguigu.product.rocketmq;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
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
@RocketMQMessageListener(consumerGroup = "orderly-consumerGroup",topic = "topicTransact",
        selectorType = SelectorType.TAG,selectorExpression = "bunnegguol",  //只消费Tag为ORDER
        consumeMode = ConsumeMode.ORDERLY //声明为顺序消费
         )
public class ProductConsumerOrderly implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 业务处理逻辑，消息会按照发送的FIFO顺序到达
        System.out.println("接收到顺序消息：" + message);
    }

}
