package bloomfilter.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.List;

public class RedisBitSet {
    private JedisPool pool;
    private final String name;
    public final int size;

    public RedisBitSet(String name, int size) {
        this.size = size;
        RedisConfigs configs = new RedisConfigs();
        RedisConnectionManager.initialize(configs);
        this.name = name;
        try {
            this.pool = RedisConnectionManager.getJedisPool();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    boolean set(int index, boolean value) {
        try (Jedis jedis = RedisConnectionManager.getJedis()) {
            return jedis.setbit(name, index, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    void pipelinedSet(int[] indexes, boolean value) {
        try (Jedis jedis = RedisConnectionManager.getJedis()) {
            Pipeline pipeline = jedis.pipelined();

            for(int index: indexes)
                pipeline.setbit(name, index, value);

            pipeline.sync();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    boolean get(int index) {
        try (Jedis jedis = RedisConnectionManager.getJedis()) {
            return jedis.getbit(name, index);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean pipelinedGet(int[] indexes) {
        try (Jedis jedis = RedisConnectionManager.getJedis()) {
            Pipeline pipeline = jedis.pipelined();
            for (int index: indexes) {
                pipeline.getbit(name, index);
            }
            List<Object> response = pipeline.syncAndReturnAll();
            return response.stream().allMatch(bit -> (boolean) bit);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}
