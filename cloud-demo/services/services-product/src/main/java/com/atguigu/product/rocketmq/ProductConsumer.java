package com.atguigu.product.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/19
 */
@Component
@RocketMQMessageListener(consumerGroup = "topic-consumerGroup",topic = "topicTransact" )
public class ProductConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage( String message){
        System.out.println("收到消息："+message);
    }

}
