package dddy.gin.practice;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * 对比 有无 pipeline 执行情况
 *
 * @author gin
 */
public class TestPipeline {
    /**
     * 100 的倍数！
     */
    private int num = 10000;
    private int oneTime = 100;
    private Jedis jedis = null;

    public TestPipeline() {
        jedis = new Jedis("192.168.99.100");
    }

    public void close(){
        if (jedis!=null){
            jedis.close();
        }
    }

    long notUsePipeline() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            jedis.hset("hashKey:" + i, "field" + i, "value" + i);
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    long usePipeline() {
        int batch = num/oneTime;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < batch; i++) {
            Pipeline pipeline = jedis.pipelined();
            for (int j = i*oneTime; j < (i+1)*oneTime; j++) {
                pipeline.hset("hashKey:" + j, "field" + j, "value" + j);
            }
            pipeline.syncAndReturnAll();
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static void main(String[] args) {
        TestPipeline testPipeline = new TestPipeline();
        System.out.println("notUsePipeline: " + testPipeline.notUsePipeline());
        System.out.println("usePipeline: " +testPipeline.usePipeline());
        testPipeline.close();
    }

}
