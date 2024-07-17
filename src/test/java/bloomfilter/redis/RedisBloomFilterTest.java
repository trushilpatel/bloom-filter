package bloomfilter.redis;

import bloomfilter.BloomFilter;
import bloomfilter.memory.InMemoryBloomFilter;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static bloomfilter.utils.Helper.generateRandomString;
import static org.junit.jupiter.api.Assertions.*;

class RedisBloomFilterTest {

    @Test
    public void testInMemoryBloomFilterImpl() {
        System.setProperty("env", "LOCAL");
        int expectedSize = 10_000;
        float fpProbability = 0.01f;
        BloomFilter bloomFilter = new RedisBloomFilter(expectedSize, fpProbability);
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
        int n = 10_000;
        int length = 12;
        for (int i = 0; i < n; i++) {
            bloomFilter.add(generateRandomString(length));
        }
        long end = System.currentTimeMillis();
        System.out.println("Add => Total time taken: " + (end - start) + " ms");


        // Check contains performance
        start = System.currentTimeMillis();
        long collision = 0;
        for (int i = 0; i < n; i++) {
            if (bloomFilter.contains(generateRandomString(length)))
                collision++;
        }
        end = System.currentTimeMillis();

        System.out.println("Contains => Total time taken: " + (end - start) + " ms");
        System.out.println("Collision: " + collision);
        System.out.println("Collision Percentage: " + ((double) (collision * 100)) / n + "%");
    }
}