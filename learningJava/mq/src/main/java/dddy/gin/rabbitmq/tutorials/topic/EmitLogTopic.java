package dddy.gin.rabbitmq.tutorials.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dddy.gin.rabbitmq.tutorials.RBConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 订阅模式：主题，发布者
 *
 * @author gin
 */
@Slf4j
public class EmitLogTopic {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(RBConfig.MQ_POST);
        factory.setHost(RBConfig.MQ_HOST);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(RBConfig.TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            String routingKey = getRouting(args);
            String message = getMessage(args);

            channel.basicPublish(RBConfig.TOPIC_EXCHANGE_NAME, routingKey, null, message.getBytes(RBConfig.CHARSET));
            log.info("[X] Sent '{}':'{}'", routingKey, message);

        }

    }

    private static String getRouting(String[] strings) {
        if (strings.length < 1)
            return "anonymous.info";
        return strings[0];
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 2)
            return "Hello World!";
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) return "";
        if (length < startIndex) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

}
