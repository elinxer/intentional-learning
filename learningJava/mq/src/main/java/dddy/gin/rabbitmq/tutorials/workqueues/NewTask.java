package dddy.gin.rabbitmq.tutorials.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import dddy.gin.rabbitmq.tutorials.RBConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 队列生产者。实现队列和消息持久化。
 * <p>
 * 需要注意的是 虽然已经设置了durable=true 和 props=MessageProperties.PERSISTENT_TEXT_PLAIN 保证队列和消息的持久化，
 * 但是这个不是强力的保证，还是会出现数据的丢失问题，在官网中有如下说明：
 * Marking messages as persistent doesn't fully guarantee that a message won't be lost.
 * Although it tells RabbitMQ to save the message to disk,
 * there is still a short time window when RabbitMQ has accepted a message and hasn't saved it yet.
 * Also, RabbitMQ doesn't do fsync(2) for every message
 * -- it may be just saved to cache and not really written to the disk.
 * The persistence guarantees aren't strong, but it's more than enough for our simple task queue.
 * If you need a stronger guarantee then you can use publisher confirms.
 *
 * @author gin
 */
@Slf4j
public class NewTask {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RBConfig.MQ_HOST);
        factory.setPort(RBConfig.MQ_POST);
        // try-with-resource statement to automatically close the channel
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //durable=true 持久化队列，重启不会丢失队列
            //需要注意的一点 如果队列已经生成，更改队列持久化策略了，可以新建一个新的队列
            channel.queueDeclare(RBConfig.TASK_QUEUE_NAME, true, false, false, null);
            String message = String.join("", args);
            //消息标记为持久性 props=MessageProperties.PERSISTENT_TEXT_PLAIN
            channel.basicPublish("", RBConfig.TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            log.info(" [x] Sent '{}'", message);

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

