package dddy.gin.rocketmq.example.simple;

import dddy.gin.rocketmq.example.RKConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Send Messages in One-way Mode
 * One-way transmission is used for cases requiring moderate reliability, such as log collection.
 * @author gin
 */
@Slf4j
public class OneWayProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer(RKConfig.SIMPLE_GROUP_NAME);
        producer.setNamesrvAddr(RKConfig.NAME_SRV_ADDR);
        producer.start();

        for (int i = 0; i < 100; i++) {
            Message msg = new Message(
                    RKConfig.SIMPLE_TOPIC,
                    RKConfig.SIMPLE_TAG,
                    ("Hello RocketMQ"+i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            producer.sendOneway(msg);
        }

    }
}
