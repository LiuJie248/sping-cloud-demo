package com.atguigu.order.rocketmq;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.StringMessageConverter;

/**
 * @author : LiuJie
 * @description : 事务消费
 * @date : 2026/9/19
 */
@RocketMQTransactionListener
public class ProductTransactionConsumer implements RocketMQLocalTransactionListener {
    /**
     * @param var1
     * @param var2
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message var1, Object var2) {
        // 发送消息时传入的业务数据
        String destination =(String) var2;
        // spring的转为RocketMQ
        org.apache.rocketmq.common.message.Message message = RocketMQUtil.convertToRocketMessage(
                new StringMessageConverter(), "utf-8", destination, var1);
        String tags = message.getTags();
        System.out.println("得到tags="+tags);
        if( StringUtils.contains(tags,"tag1") ){
            // 如果数据库提交成功，返回 COMMIT_MESSAGE 让 MQ 投递消息
            return RocketMQLocalTransactionState.COMMIT;
        } else if ( StringUtils.contains(tags,"tag2") ) {
            // 如果数据库提交成功，返回 COMMIT_MESSAGE 让 MQ 投递消息
            return RocketMQLocalTransactionState.ROLLBACK;
        }else {
            return RocketMQLocalTransactionState.UNKNOWN;
        }

    }

    /**
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        return null;
    }
}
