package dddy.gin.rabbitmq.tutorials.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dddy.gin.rabbitmq.tutorials.RBConfig;
import lombok.extern.slf4j.Slf4j;


/**
 * 发布订阅 type=direct
 * 发布者
 *
 * @author gin
 */

@Slf4j
public class EmitLogDirect {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(RBConfig.MQ_POST);
        factory.setHost(RBConfig.MQ_HOST);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(RBConfig.DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String severity = getSeverity(args);
            String message = getMessage(args);

            channel.basicPublish(RBConfig.DIRECT_EXCHANGE_NAME, severity, null, message.getBytes());
            log.info(" [x] Sent '{}':'{}' ", severity, message);

        }

    }

    private static String getSeverity(String[] ages) {
        if (ages.length < 1) {
            return "info";
        }

        return ages[0];
    }

    private static String getMessage(String[] ages) {
        if (ages.length < 2) {
            return "Hello World";
        }
        return joinStrings(ages, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0 || length <= startIndex) {
            return "";
        }
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

}
