package dddy.gin.rabbitmq.tutorials.fanout;

import com.rabbitmq.client.*;
import dddy.gin.rabbitmq.tutorials.RBConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 发布订阅：type=fanout
 * 订阅者
 *
 * @author gin
 */

@Slf4j
public class ReceiveLogs {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RBConfig.MQ_HOST);
        factory.setPort(RBConfig.MQ_POST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(RBConfig.FANOUT_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        //创建一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        //队列绑定交换机
        channel.queueBind(queueName, RBConfig.FANOUT_EXCHANGE_NAME, "");

        log.info("[*] Wating for message. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            log.info(" [*] Received '{}'", message);
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });


    }
}
