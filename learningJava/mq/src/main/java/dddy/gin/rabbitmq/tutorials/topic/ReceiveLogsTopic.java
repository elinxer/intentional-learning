package dddy.gin.rabbitmq.tutorials.topic;

import com.rabbitmq.client.*;
import dddy.gin.rabbitmq.tutorials.RBConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 订阅模式：主题,订阅者
 *
 * @author gin
 */
@Slf4j
public class ReceiveLogsTopic {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(RBConfig.MQ_POST);
        factory.setHost(RBConfig.MQ_HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(RBConfig.TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();

        if (argv.length < 1) {
            log.error("Usage: ReceiveLogsTopic [binding_key]...");
            System.exit(1);
        }

        for (String bindingKey : argv) {
            channel.queueBind(queueName, RBConfig.TOPIC_EXCHANGE_NAME, bindingKey);
        }

        log.info(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), RBConfig.CHARSET);
            log.info(" [x] Received '{}':'{}'", delivery.getEnvelope().getRoutingKey(), message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

}
