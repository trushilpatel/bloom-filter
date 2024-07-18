package bloomfilter.redis;

import bloomfilter.BloomFilter;
import bloomfilter.hashFunctions.HashMethod;
import bloomfilter.hashFunctions.MurmurHash;

import java.util.BitSet;

public class RedisBloomFilter implements BloomFilter {
    private RedisBitSet bloom;
    private int bloomSize;
    private int expectedElements;
    // False Positive probability
    private float fpProbability;
    private int hashFunctions;
    private HashMethod hm1;
    private HashMethod hm2;

    public RedisBloomFilter(String name, int expectedElements, float fpProbability) {
        initialise(name, expectedElements, fpProbability);
    }

    private void initialise(String name, int expectedElements, float fpProbability) {
        this.expectedElements = expectedElements;
        this.fpProbability = fpProbability;
        this.bloomSize = calculateBloomSize();
        this.bloom = new RedisBitSet(name, this.bloomSize);
        this.hashFunctions = this.calculateHashFunctions();
        this.hm1 = new MurmurHash(1);
        this.hm2 = new MurmurHash(2);
    }

    @Override
    public boolean initialiseBloom(String name, int expectedElements, float fpProbability) {
        initialise(name, expectedElements, fpProbability);
        return false;
    }

    @Override
    public boolean clear() {
       return bloom.clear();
    }

    @Override
    public void add(byte[] b) {
        int h1 = hm1.hash(b);
        int h2 = hm2.hash(b);


        int[] indexes = new int[this.hashFunctions];
        for (int fn = 1; fn <= this.hashFunctions; fn++) {
            int hash = Math.abs(h1 + h2 * fn) % this.bloomSize;
            //bloom.set(hash, true);
            indexes[fn - 1] = hash;
        }

        bloom.pipelinedSet(indexes, true);
    }

    @Override
    public boolean contains(byte[] b) {
        int h1 = hm1.hash(b);
        int h2 = hm2.hash(b);

        int[] indexes = new int[this.hashFunctions];
        for (int fn = 1; fn <= this.hashFunctions; fn++) {
            int hash = Math.abs(h1 + h2 * fn) % this.bloomSize;
            //bloom.set(hash, true);
            indexes[fn - 1] = hash;
        }

        return bloom.pipelinedGet(indexes);
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
