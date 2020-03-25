package dddy.gin.rabbitmq.tutorials.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dddy.gin.rabbitmq.tutorials.RBConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * hello world 发送者
 *
 * @author gin
 */
@Slf4j
public class Send {

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RBConfig.MQ_HOST);
        factory.setPort(RBConfig.MQ_POST);
        // try-with-resource statement to automatically close the channel
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(RBConfig.HELLO_WORLD_QUEUE_NAME, false, false, false, null);
            String message = "Hello World！";
            channel.basicPublish("", RBConfig.HELLO_WORLD_QUEUE_NAME, null, message.getBytes());
            log.info(" [x] Sent '{}'", message);

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
