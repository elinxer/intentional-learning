package dddy.gin.rocketmq.example.order;

import dddy.gin.rocketmq.example.RKConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class OrderedConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(RKConfig.ORDER_GROUP_NAME);
        consumer.setNamesrvAddr(RKConfig.NAME_SRV_ADDR);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(RKConfig.ORDER_TOPIC, "TagA || TagB || TagD");
        consumer.registerMessageListener(
                new MessageListenerOrderly() {
                    AtomicLong consumeTimes = new AtomicLong(0);

                    @Override
                    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                        context.setAutoCommit(false);
                        log.info("{} Receive New Messages: {}", Thread.currentThread().getName(), msgs);
                        this.consumeTimes.incrementAndGet();
                        if ((this.consumeTimes.get() % 2) == 0) {
                            return ConsumeOrderlyStatus.SUCCESS;
                        } else if ((this.consumeTimes.get() % 3) == 0) {
                            return ConsumeOrderlyStatus.ROLLBACK;
                        } else if ((this.consumeTimes.get() % 4) == 0) {
                            return ConsumeOrderlyStatus.COMMIT;
                        } else if ((this.consumeTimes.get() % 5) == 0) {
                            context.setSuspendCurrentQueueTimeMillis(3000);
                            return ConsumeOrderlyStatus.COMMIT;
                        }
                        return ConsumeOrderlyStatus.SUCCESS;
                    }
                }

        );
        consumer.start();
        log.info("Consumer Started");
    }

}
