package dddy.gin.practice;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 演示故障转移
 */
@Slf4j
public class RedisSentinelFailOver {
    private static final String MASTER_NAME = "mymaster";

    public static void main(String[] args) {
        Set<String> sentinels = new HashSet<>();
        sentinels.add("127.0.0.1:26379");
        sentinels.add("127.0.0.1:26380");
        sentinels.add("127.0.0.1:26381");
        JedisSentinelPool sentinelPool = new JedisSentinelPool(MASTER_NAME, sentinels);

        while (true) {
            Jedis jedis = null;

            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                jedis = sentinelPool.getResource();
                int index = new Random().nextInt(100000);
                String key = "k-" + index;
                String value = "v-" + index;
                jedis.set(key, value);
                log.info("{} value is {}", key, value);
            } catch (Exception e) {
                log.warn(e.getMessage());
            } finally {
                if (jedis != null)
                    jedis.close();
            }
        }

    }
}
