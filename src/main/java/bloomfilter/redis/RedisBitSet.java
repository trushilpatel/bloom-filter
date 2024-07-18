package bloomfilter.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class RedisBitSet {
    private final JedisPool pool;
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
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        try (Jedis jedis = pool.getResource()) {
            return jedis.setbit(name, index, value);
        }
    }

    void pipelinedSet(int[] indexes, boolean value) {
        try (Jedis jedis = pool.getResource()) {
            Pipeline pipeline = jedis.pipelined();
            for (int index : indexes) {
                if (index >= size)
                    throw new IndexOutOfBoundsException("Index out of bounds: " + index);
                pipeline.setbit(name, index, value);
            }
            pipeline.sync();
        }
    }

    boolean get(int index) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.getbit(name, index);
        }
    }

    boolean pipelinedGet(int[] indexes) {
        try (Jedis jedis = pool.getResource()) {
            Pipeline pipeline = jedis.pipelined();
            for (int index : indexes) {
                if (index >= size)
                    throw new IndexOutOfBoundsException("Index out of bounds: " + index);
                pipeline.getbit(name, index);
            }
            List<Object> response = pipeline.syncAndReturnAll();
            return response.stream().allMatch(bit -> (boolean) bit);
        }
    }

    boolean clear() {
        try (Jedis jedis = pool.getResource()) {
            Pipeline pipeline = jedis.pipelined();
            pipeline.del(name);
            return true;
        }
    }
}
