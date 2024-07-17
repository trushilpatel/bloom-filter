package bloomfilter;

import bloomfilter.redis.RedisConfigs;
import bloomfilter.redis.RedisConnectionManager;
import redis.clients.jedis.Jedis;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        try {
            RedisConfigs configs = new RedisConfigs("redis.properties");

            RedisConnectionManager.initialize(configs);

            Jedis jedis = RedisConnectionManager.getJedis();
            jedis.set("Hello", "World");
        } catch (IOException | IllegalAccessException e) {
            System.out.println("Failed to load Redis Configuration: " + e.getMessage());
        }
    }
}