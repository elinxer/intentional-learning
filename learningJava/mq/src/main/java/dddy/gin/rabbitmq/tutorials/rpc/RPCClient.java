package dddy.gin.rabbitmq.tutorials.rpc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dddy.gin.rabbitmq.tutorials.RBConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * RPC 客户端
 *
 * @author gin
 */

@Slf4j
public class RPCClient implements AutoCloseable {

    private Connection connection;
    private Channel channel;

    public RPCClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RBConfig.MQ_HOST);
        factory.setPort(RBConfig.MQ_POST);

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public static void main(String[] args) {
        try (RPCClient fibonacciRpc = new RPCClient()) {
            for (int i = 0; i < 32; i++) {
                String i_str = Integer.toString(i);
                log.info(" [x] Requesting fib({})", i_str);
                String response = fibonacciRpc.call(i_str);
                log.info(" [.] Got '{}'", response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String call(String message) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", RBConfig.RPC_EXCHANGE_NAME, props, message.getBytes(RBConfig.CHARSET));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.offer(new String(delivery.getBody(), RBConfig.CHARSET));
            }
        }, consumerTag -> {
        });
        String result = response.take();
        channel.basicCancel(ctag);
        return result;

    }

    @Override
    public void close() throws Exception {
        connection.close();

    }
}
