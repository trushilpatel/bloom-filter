package bloomfilter.redis;

import bloomfilter.BloomFilter;
import bloomfilter.memory.InMemoryBloomFilter;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static bloomfilter.utils.Helper.generateRandomString;
import static org.junit.jupiter.api.Assertions.*;

class RedisBloomFilterTest {

    @Test
    public void testInMemoryBloomFilterImpl() {
        System.setProperty("env", "LOCAL");
        int expectedSize = 100_000;
        float fpProbability = 0.01f;
        String name = "bloom-filter";

        BloomFilter bloomFilter = new RedisBloomFilter(name, expectedSize, fpProbability);
        try (Jedis jedis = RedisConnectionManager.getJedis()) {
            jedis.del(name);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        System.out.println(bloomFilter);


        // must contain string
        String s1 = "Hi";
        bloomFilter.add(s1);
        assertTrue(bloomFilter.contains(s1));

        // should not contain string
        String s2 = "Hey";
        assertFalse(bloomFilter.contains(s2));

        // Check add performance
        long start = System.currentTimeMillis();
        int n = 100_000;
        int length = 12;
        IntStream.range(0,n)
                .parallel()
                .forEach(i -> bloomFilter.add(generateRandomString(length)));

        long end = System.currentTimeMillis();
        System.out.println("Add => Total time taken: " + (end - start) + " ms");


        // Check contains performance
        start = System.currentTimeMillis();

        AtomicInteger collision = new AtomicInteger(0);

        IntStream.range(0, n)
                .parallel() // Execute in parallel
                .mapToObj(i -> bloomFilter.contains(generateRandomString(length)))
                .forEach(element -> {
                    if (element) {
                        collision.incrementAndGet();
                    }
                });
        end = System.currentTimeMillis();

        System.out.println("Contains => Total time taken: " + (end - start) + " ms");
        System.out.println("Collision: " + collision);
        System.out.println("Collision Percentage: " + ((double) (collision.get() * 100)) / n + "%");
    }
}