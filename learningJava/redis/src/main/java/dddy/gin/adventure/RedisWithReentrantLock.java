package dddy.gin.adventure;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.Map;

/**
 * 第02章，可重入锁的实现
 *
 * @author gin
 */
public class RedisWithReentrantLock {

    private ThreadLocal<Map<String, Integer>> locker = new ThreadLocal<>();

    private Jedis jedis;

    public RedisWithReentrantLock(Jedis jedis) {
        this.jedis = jedis;
    }

    private boolean _lock(String key) {
        return jedis.set(key, "", SetParams.setParams().nx().ex(5)) != null;
    }

    private void _unlock(String key) {
        jedis.del(key);
    }

    private Map<String, Integer> currentLockers() {
        Map<String, Integer> refs = locker.get();
        if (refs != null) {
            return refs;
        }
        locker.set(new HashMap<>());
        return locker.get();
    }

    public boolean lock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt != null) {
            refs.put(key, refCnt + 1);
            return true;
        }
        boolean ok = this._lock(key);
        if (!ok) {
            return false;
        }
        refs.put(key, 1);
        return true;
    }

    public boolean unlock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt == null){
            return false;
        }
        refCnt -= 1;
        if (refCnt>0){
            refs.put(key,refCnt);
        }
        refs.remove(key);
        return true;
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.99.100");
        RedisWithReentrantLock redis = new RedisWithReentrantLock(jedis);
        System.out.println(redis.lock("codehole"));
        System.out.println(redis.lock("codehole"));
        System.out.println(redis.unlock("codehole"));
        System.out.println(redis.unlock("codehole"));
    }


}
