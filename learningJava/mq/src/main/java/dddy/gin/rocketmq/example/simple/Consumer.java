package dddy.gin.rocketmq.example.simple;

import dddy.gin.rocketmq.example.RKConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Consume Messages
 *
 * @author gin
 */
@Slf4j
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        //Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(RKConfig.SIMPLE_GROUP_NAME);

        //Specify name server address
        consumer.setNamesrvAddr(RKConfig.NAME_SRV_ADDR);

        // Subscribe one more more topics to consume.
        consumer.subscribe(RKConfig.SIMPLE_TOPIC, "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(
                (MessageListenerConcurrently) (list, context) -> {
                    log.info("{} Receive New Message:{}", Thread.currentThread().getName(), list);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
        );
        //Launch the consumer instance.
        consumer.start();
        log.info("Consume Start.");


    }
}
