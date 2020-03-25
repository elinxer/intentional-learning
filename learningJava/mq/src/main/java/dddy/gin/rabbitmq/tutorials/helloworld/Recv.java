package dddy.gin.rabbitmq.tutorials.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import dddy.gin.rabbitmq.tutorials.RBConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * hello world 发送者
 *
 * @author gin
 */
@Slf4j
public class Recv {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RBConfig.MQ_HOST);
        factory.setPort(RBConfig.MQ_POST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(RBConfig.HELLO_WORLD_QUEUE_NAME, false, false, false, null);
        log.info("[*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), RBConfig.CHARSET);
            log.info(" [x] Receiver '{}' ", message);
        };
        channel.basicConsume(RBConfig.HELLO_WORLD_QUEUE_NAME, true, deliverCallback, consumer -> {
        });


    }


}
