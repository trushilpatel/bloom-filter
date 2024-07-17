package bloomfilter;

import java.nio.charset.StandardCharsets;

public interface BloomFilter {

    // insert string into the bloom
    void add(byte[] b);

    default void add(String s) {
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        add(b);
    }

    // checks if the bloom contains the string
    boolean contains(byte[] b);

    default boolean contains(String s) {
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        return contains(b);
    }

    // false positive probability
    float getFalsePositiveProbability();

    // calculate and return number of functions to be used for bloom filter
    int calculateHashFunctions();

    // calculates and returns the size of the bloom filter
    // based on number of expected items and hash collition probability
    int calculateBloomSize();
}
