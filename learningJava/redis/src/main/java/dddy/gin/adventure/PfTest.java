package dddy.gin.adventure;

import redis.clients.jedis.Jedis;

/**
 * HyperLogLog 实现
 * @author gin
 */
public class PfTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.99.100", 6379);
        int num = 100000;
        for (int i = 0; i < num; i++) {
            jedis.pfadd("codehole", "user" + i);
        }
        long total = jedis.pfcount("codehole");
        System.out.println(total);
        jedis.close();
    }
}
