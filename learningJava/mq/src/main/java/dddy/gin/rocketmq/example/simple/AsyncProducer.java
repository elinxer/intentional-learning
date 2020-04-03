package dddy.gin.rocketmq.example.simple;

import dddy.gin.rocketmq.example.RKConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Send Messages Asynchronously
 * Asynchronously transmission is generally used in response time sensitive business scenarios
 *
 * @author gin
 */
@Slf4j
public class AsyncProducer {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer(RKConfig.SIMPLE_GROUP_NAME);
        producer.setNamesrvAddr(RKConfig.NAME_SRV_ADDR);
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        for (int i = 0; i < 100; i++) {
            final int index = i;
            Message msg = new Message(
                    RKConfig.SIMPLE_TOPIC,
                    RKConfig.SIMPLE_TAG,
                    "OrderID188",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("{} OK {}", index, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    log.info("{} Exception {}", index, e.getMessage());
                    //e.printStackTrace();
                }
            });

        }
        //producer.shutdown();


    }
}
