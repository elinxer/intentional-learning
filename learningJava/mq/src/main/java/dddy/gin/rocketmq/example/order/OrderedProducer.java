package dddy.gin.rocketmq.example.order;

import dddy.gin.rocketmq.example.RKConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

@Slf4j
public class OrderedProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(RKConfig.ORDER_GROUP_NAME);
        producer.setNamesrvAddr(RKConfig.NAME_SRV_ADDR);
        producer.start();
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 100; i++) {
            int orderId = i % 10;

            Message msg = new Message(
                    RKConfig.ORDER_TOPIC,
                    tags[i % tags.length],
                    "Key" + i,
                    ("Hello RocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            SendResult sendResult = producer.send(
                    msg,
                    (mqs, msg1, arg) -> {
                        Integer id = (Integer) arg;
                        int index = id % mqs.size();
                        return mqs.get(index);
                    }
                    , orderId
            );
            log.info("{}", sendResult);
        }
        producer.shutdown();
    }
}
