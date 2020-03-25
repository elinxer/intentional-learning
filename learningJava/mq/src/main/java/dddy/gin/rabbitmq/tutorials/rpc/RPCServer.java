package dddy.gin.rabbitmq.tutorials.rpc;

import com.rabbitmq.client.*;
import dddy.gin.rabbitmq.tutorials.RBConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * RPC 服务端
 *
 * @author gin
 */
@Slf4j
public class RPCServer {

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(RBConfig.MQ_POST);
        factory.setHost(RBConfig.MQ_HOST);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(RBConfig.RPC_EXCHANGE_NAME, false, false, false, null);
            channel.queuePurge(RBConfig.RPC_EXCHANGE_NAME);

            channel.basicQos(1);

            log.info("[x] Awaiting RPC requests");

            Object monitor = new Object();
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String response = "";

                try {
                    String message = new String(delivery.getBody(), RBConfig.CHARSET);
                    int n = Integer.parseInt(message);

                    log.info(" [.] fib({})", message);
                    response += fib(n);

                } catch (RuntimeException e) {
                    log.error(" [.] {}", e.toString());
                } finally {
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    // RabbitMq consumer worker thread notifies the RPC server owner thread
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
            };

            channel.basicConsume(RBConfig.RPC_EXCHANGE_NAME,deliverCallback,(consumerTag -> { }));
            // Wait and be prepared to consume the message from RPC client.
            while (true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }



        }

    }

}
