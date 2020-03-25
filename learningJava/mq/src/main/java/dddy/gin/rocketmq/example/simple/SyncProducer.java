package dddy.gin.rocketmq.example.simple;

import dddy.gin.rocketmq.example.RKConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Send Messages Synchronously
 * Reliable synchronous transmission is used in extensive scenes,
 * such as important notification messages, SMS notification, SMS marketing system, etc.
 *
 * @author gin
 */
@Slf4j
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        // Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(RKConfig.SIMPLE_GROUP_NAME);
        // Specify name server addresses.
        producer.setNamesrvAddr(RKConfig.NAME_SRV_ADDR);
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 100; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message(
                    RKConfig.SIMPLE_TOPIC,
                    RKConfig.SIMPLE_TAG,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            log.info("Send Result:{}", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
