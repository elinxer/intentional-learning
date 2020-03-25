package dddy.gin.rabbitmq.tutorials;

/**
 * rabbitmq tutorials 主要配置
 *
 * @author gin
 */
public class RBConfig {

    public static final String MQ_HOST = "192.168.99.100";

    public static final int MQ_POST = 5672;

    public final static String HELLO_WORLD_QUEUE_NAME = "hello";

    public final static String TASK_QUEUE_NAME = "task_queue";

    public final static String FANOUT_EXCHANGE_NAME = "log";

    public final static String DIRECT_EXCHANGE_NAME = "direct_log";

    public final static String TOPIC_EXCHANGE_NAME = "topic_logs";

    public final static String RPC_EXCHANGE_NAME = "rpc_queue";

    public final static String CHARSET = "UTF-8";
}
