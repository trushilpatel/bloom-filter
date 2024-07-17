package bloomfilter.redis;

import bloomfilter.BloomFilter;
import bloomfilter.hashFunctions.HashMethod;
import bloomfilter.hashFunctions.MurmurHash;

import java.util.BitSet;

public class RedisBloomFilter implements BloomFilter {
    private final RedisBitSet bloom;
    private final int bloomSize;
    private final int expectedElements;
    // False Positive probability
    private final float fpProbability;
    private final int hashFunctions;
    private final HashMethod hm1;
    private final HashMethod hm2;

    RedisBloomFilter(int expectedElements, float fpProbability) {
        this.expectedElements = expectedElements;
        this.fpProbability = fpProbability;
        this.bloomSize = calculateBloomSize();
        this.bloom = new RedisBitSet("bloom-filter", this.bloomSize);
        this.hashFunctions = this.calculateHashFunctions();
        this.hm1 = new MurmurHash(1);
        this.hm2 = new MurmurHash(2);
    }

    @Override
    public void add(byte[] b) {
        int h1 = hm1.hash(b);
        int h2 = hm2.hash(b);

        for (int fn = 1; fn <= this.hashFunctions; fn++) {
            int hash = Math.abs(h1 + h2 * fn) % this.bloomSize;
            bloom.set(hash, true);
        }
    }

    @Override
    public boolean contains(byte[] b) {
        int h1 = hm1.hash(b);
        int h2 = hm2.hash(b);

        for (int fn = 1; fn <= this.hashFunctions; fn++) {
            int hash = Math.abs(h1 + h2 * fn) % this.bloomSize;
            if (!bloom.get(hash))
                return false;
        }

        return true;
    }

    @Override
    public float getFalsePositiveProbability() {
        return this.fpProbability;
    }

    @Override
    public int calculateHashFunctions() {
        return (int) ((this.bloomSize / this.expectedElements) * Math.log(2));
    }

    // bloomSize = - size * ln(fpProbability) / ln(2)^2
    @Override
    public int calculateBloomSize() {
        return (int) (-this.expectedElements * Math.log(this.fpProbability) / (Math.pow(Math.log(2), 2)));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BloomSize: ").append(this.bloomSize).append("\n")
                .append("False Positive Probability: ").append(this.fpProbability).append("\n")
                .append("Expected Elements: ").append(this.expectedElements).append("\n")
                .append("HashMethod: ").append(hashFunctions).append("\n");

        return sb.toString();
    }
}
