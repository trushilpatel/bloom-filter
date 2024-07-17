package bloomfilter.hashFunctions;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class MurmurHash implements HashMethod {
    private static final int C1 = 0xcc9e2d51;
    private static final int C2 = 0x1b873593;
    private static final int R1 = 15;
    private static final int R2 = 13;
    private static final int M = 5;
    private static final int N = 0xe6546b64;
    private final int DEFAULT_SEED;

    public MurmurHash() {
        DEFAULT_SEED = 0;
    }

    public MurmurHash(int seed) {
        this.DEFAULT_SEED = seed;
    }

    @Override
    public int hash(final byte[] data) {
        return hash(data, DEFAULT_SEED);
    }

    public int hash(final byte[] data, int seed) {
        int hash = seed;
        int length = data.length;
        int i = 0;
        while (i + 4 <= length) {
            int k = ByteBuffer.wrap(data, i, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
            k *= C1;
            k = Integer.rotateLeft(k, R1);
            k *= C2;

            hash ^= k;
            hash = Integer.rotateLeft(hash, R2) * M + N;

            i += 4;
        }

        int k = 0;
        switch (length & 3) {
            case 3:
                k ^= (data[i + 2] & 0xff) << 16;
            case 2:
                k ^= (data[i + 1] & 0xff) << 8;
            case 1:
                k ^= (data[i] & 0xff);
                k *= C1;
                k = Integer.rotateLeft(k, R1);
                k *= C2;
                hash ^= k;
        }

        hash ^= length;
        hash ^= (hash >>> 16);
        hash *= 0x85ebca6b;
        hash ^= (hash >>> 13);
        hash *= 0xc2b2ae35;
        hash ^= (hash >>> 16);

        return hash;
    }
}
