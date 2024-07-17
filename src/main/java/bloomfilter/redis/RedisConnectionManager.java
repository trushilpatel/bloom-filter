package bloomfilter.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnectionManager {
    private static JedisPool jedisPool;

    public static void initialize(RedisConfigs config) {
        if (jedisPool == null) {
            JedisPoolConfig poolConfig = new JedisPoolConfig();

            poolConfig.setMaxTotal(config.getMaxTotal());
            poolConfig.setMaxIdle(config.getMaxIdle());
            poolConfig.setMinIdle(config.getMinIdle());
            poolConfig.setTestOnBorrow(config.isTestOnBorrow());
            poolConfig.setTestOnReturn(config.isTestOnReturn());
            poolConfig.setTestWhileIdle(config.isTestWhileIdle());
            poolConfig.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
            poolConfig.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEvictionRunsMillis());
            poolConfig.setNumTestsPerEvictionRun(config.getNumTestsPerEvictionRun());

            jedisPool = new JedisPool(poolConfig, config.getHost(), config.getPort(), config.getTimeout(), config.getPassword());
        }
    }

    public static Jedis getJedis() throws IllegalAccessException {
        if (jedisPool == null) {
            throw new IllegalAccessException("JedisPool not initialized. Call initialize() first.");
        }
        return jedisPool.getResource();
    }

    public static void shutdown() {
        if (jedisPool != null) {
            jedisPool.clear();
        }
    }
}
