package dddy.gin.rabbitmq.tutorials.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import dddy.gin.rabbitmq.tutorials.RBConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 队列消费者。
 * 实现了队列持久化、消息手动应答和 不使用公平分发（Fair dispatch，生产者会轮询分发消息给消费者，无论消费者是否完成）
 * 其中 prefetchCount=1 官网有这段话：
 * In order to defeat "Fair dispatch" we can use the basicQos method with the prefetchCount = 1 setting.
 * This tells RabbitMQ not to give more than one message to a worker at a time.
 * Or, in other words, don't dispatch a new message to a worker until it has processed and acknowledged the previous one.
 * Instead, it will dispatch it to the next worker that is not still busy
 * 需要注意的是：关于队列大小
 * 因为  prefetchCount=1 ，而且你的消息队列都是满的，这样消息就会积压在队列，
 * 但是又不能发送给消费者，这时应该扩展消费者的数量等策略。
 * 官网原文：
 * If all the workers are busy, your queue can fill up. You will want to keep an eye on that,
 * and maybe add more workers, or have some other strategy.
 *
 * @author gin
 */
@Slf4j
public class Worker {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RBConfig.MQ_HOST);
        factory.setPort(RBConfig.MQ_POST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //durable=true 持久化队列，重启不会丢失队列。
        //需要注意的一点 如果队列已经生成，更改队列持久化策略了，可以新建一个新的队列
        channel.queueDeclare(RBConfig.TASK_QUEUE_NAME, true, false, false, null);
        log.info("[*] Waiting for messages. To exit press CTRL+C");

        // prefetchCount=1 消费者同一时刻只能接受一条消息。
        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), RBConfig.CHARSET);

            log.info(" [x] Receiver '{}' ", message);
            try {
                doWork(message);
            } finally {
                log.info("[x] Done");
                //确认消息已经收到
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }

        };

        // autoAck = false 是否自动确认消息已经送达
        channel.basicConsume(RBConfig.TASK_QUEUE_NAME, false, deliverCallback, consumer -> {
        });


    }

    private static void doWork(String task) {
        try {
            for (char ch : task.toCharArray()) {
                if (ch == '.') {
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
