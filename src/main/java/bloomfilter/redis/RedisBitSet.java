package bloomfilter.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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

    boolean get(int index) {
        try (Jedis jedis = RedisConnectionManager.getJedis()) {
            return jedis.getbit(name, index);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}
