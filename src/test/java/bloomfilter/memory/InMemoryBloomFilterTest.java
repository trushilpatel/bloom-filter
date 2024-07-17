package bloomfilter.memory;


import bloomfilter.BloomFilter;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryBloomFilterTest {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Test
    public void testInMemoryBloomFilterImpl() {
        int expectedSize = 10_000_000;
        float fpProbability = 0.01f;
        BloomFilter bloomFilter = new InMemoryBloomFilter(expectedSize, fpProbability);
        System.out.println(bloomFilter);


        // must contain string
        String s1 = "Hello";
        bloomFilter.add(s1);
        assertTrue(bloomFilter.contains(s1));

        // should not contain string
        String s2 = "Hey";
        assertFalse(bloomFilter.contains(s2));

        // Check add performance
        long start = System.currentTimeMillis();
        int n = 1_000_000;
        int length = 25;
        for (int i = 0; i < n; i++) {
            bloomFilter.add(generateRandomString(length));
        }
        long end = System.currentTimeMillis();
        System.out.println("Add => Total time taken: " + (end - start) + " ms");


        // Check contains performance
        start = System.currentTimeMillis();
        long collision = 0;
        for (int i = 0; i < n; i++) {
            if(bloomFilter.contains(generateRandomString(length)))
                collision++;
        }
        end = System.currentTimeMillis();

        System.out.println("Contains => Total time taken: " + (end - start) + " ms");
        System.out.println("Collision: " + collision );
        System.out.println("Collision Percentage: "  + ((double)(collision*100))/n + "%");
    }

    public static String generateRandomString(int length) {
        Random random = new Random();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}