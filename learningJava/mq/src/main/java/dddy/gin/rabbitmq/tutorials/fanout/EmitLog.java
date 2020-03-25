package dddy.gin.rabbitmq.tutorials.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dddy.gin.rabbitmq.tutorials.RBConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 发布订阅：type=fanout
 * 发布者
 *
 * @author gin
 */

@Slf4j
public class EmitLog {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RBConfig.MQ_HOST);
        factory.setPort(RBConfig.MQ_POST);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //"FANOUT":广播模式，只要接入 exchange 的队列就能收到消息
            channel.exchangeDeclare(RBConfig.FANOUT_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            String message = args.length < 1 ? "info: Hello World" :
                    String.join("", args);
            channel.basicPublish(RBConfig.FANOUT_EXCHANGE_NAME, "", null, message.getBytes());

            log.info(" [x] Sent '{}'", message);
        }

    }

}
