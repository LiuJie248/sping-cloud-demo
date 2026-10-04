package com.atguigu.order.rocketmq;

import com.atguigu.order.mapper.OrderTblMapper;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : LiuJie
 * @description : TODO
 * @date : 2026/9/19
 */
@RestController
@RequestMapping("/orderSyncProducer")
public class OrderSyncProducer {

    @Autowired
    private OrderTblMapper orderTblMapper;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @PostMapping("/sendmessage")
    public void sendmessage(String topic,String message ){
        rocketMQTemplate.convertAndSend(topic,message);
    }

    /**
     *  异步,不阻塞，立即返回
      */
    @PostMapping("/sendAsyncMessage")
    public void sendAsyncMessage() {
        // 异步发送消息
        rocketMQTemplate.asyncSend("test-topic:test-tag",
                "异步消息主线程会立即返回继续执行后续代码，不需要等待 Broker 返回发送结果",new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 消息发送成功后的回调处理
                System.out.println("发送成功，消息ID：" + sendResult.getMsgId());
            }
            @Override
            public void onException(Throwable throwable) {
                // 消息发送失败后的异常处理
                System.err.println("发送失败，异常信息：" + throwable.getMessage());
            }
        });
        // 注意：这里会先打印，因为主线程不会等待上面的回调执行
        System.out.println("主线程继续执行后续逻辑");
    }

    /**
     * 发事务消息
     * @param topic
     * @param msg
     */
    @PostMapping("/sendTransactionmessage")
    public void sendTransactionmessage(String topic,String msg ){
        String[] tags =new String[]{"tag1","tag2","tag3","tag4", "tag5"};
        for( int i=0;i<10;i++){
            Message<String> message=MessageBuilder.withPayload(msg).build();
            // topic tag 整合在一起
            String destination=topic+":"+tags[i % tags.length];
            // 目的地、消息（事务成功会发送的）、业务参数
            TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(destination, message, destination);
            System.out.println("发送结果"+transactionSendResult);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @PostMapping("/createOrder")
    @Transactional
    public void createOrder(String userId,String commodityCode ){
        // 建议一个服务只用一个topic，使用tag进行过滤
        String topic ="topicTransact";
        // 新增订单
        Map<String, Object> params = new HashMap<>();
        params.put("commodityCode",commodityCode);
        params.put("userId", userId);
        int row = orderTblMapper.insert(params);
        BigInteger id = (BigInteger) params.get("id");  // 主键回填到 Map 中

        String headerValue = "ORDER_20260920_"+id;
        // 使用 MessageBuilder（推荐，配合 RocketMQTemplate 使用）
        Message<String> message = MessageBuilder.withPayload(userId+","+commodityCode)
                .setHeader(MessageConst.PROPERTY_KEYS, headerValue) // 设置业务唯一Key
                .build();
        // 同步发送，可以获取 msgId、sendStatus 等详细信息
        SendResult sendResult = rocketMQTemplate.syncSend(topic+":"+"createOrder", message);
    }

    @GetMapping("/sendOrderedMessage")
    public void sendOrderedMessage() {
        // topic: 目标主题，// 建议一个服务只用一个topic，使用tag进行过滤
        String topic ="topicTransact";
        for( int orderId=0; orderId<10; orderId++ ){
// messageContent: 消息内容，orderId: 作为 hashKey，保证同一订单的消息发到同一队列，必须使用 syncSendOrderly 同步发送。
            rocketMQTemplate.syncSendOrderly(topic+":"+"bunnegguol","messageContent",String.valueOf(orderId));
            System.out.println("发送成功"+orderId);
        }
    }

}
