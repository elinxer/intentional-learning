package dddy.gin.rabbitmq.tutorials.direct;

import com.rabbitmq.client.*;
import dddy.gin.rabbitmq.tutorials.RBConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gin
 */
@Slf4j
public class ReceiveLogsDirect {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RBConfig.MQ_HOST);
        factory.setPort(RBConfig.MQ_POST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(RBConfig.DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();

        if (args.length < 1) {
            log.error("Usage: ReceiveLogsDirect [error]");
            System.exit(1);
        }

        for (String severity : args) {
            channel.queueBind(queueName, RBConfig.DIRECT_EXCHANGE_NAME, severity);
        }
        log.info("[*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), RBConfig.CHARSET);
            log.info("[x] Received '{}':'{}'", delivery.getEnvelope().getRoutingKey(), message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });

    }
}
