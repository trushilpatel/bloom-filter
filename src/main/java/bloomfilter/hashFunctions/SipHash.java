package bloomfilter.hashFunctions;

public final class SipHash implements HashMethod {
    private static final long K0 = 0x736f6d6570736575L;
    private static final long K1 = 0x646f72616e646f6dL;
    private static final int C = 2;
    private static final int D = 4;

    public SipHash() {
    }

    @Override
    public int hash(byte[] data) {
        return (int) sipHash24(data, 0, data.length, K0, K1);
    }

    public static long sipHash24(byte[] data, int offset, int length, long k0, long k1) {
        long v0 = K0 ^ k0;
        long v1 = K1 ^ k1;
        long v2 = K0 ^ k0;
        long v3 = K1 ^ k1;

        long m;
        int index = offset;
        int remaining = length;

        while (remaining >= D) {
            m = ((long) data[index++] & 0xff) |
                    (((long) data[index++] & 0xff) << 8) |
                    (((long) data[index++] & 0xff) << 16) |
                    (((long) data[index++] & 0xff) << 24);

            v3 ^= m;

            sipRound(v0, v1, v2, v3);

            v0 ^= m;

            remaining -= D;
        }

        m = 0;
        for (int i = 0; i < remaining; i++) {
            m |= ((long) data[index++] & 0xff) << (i * 8);
        }

        m |= (long) length << 56;

        v3 ^= m;

        sipRound(v0, v1, v2, v3);

        v0 ^= m;

        v2 ^= 0xff;

        sipRound(v0, v1, v2, v3);
        sipRound(v0, v1, v2, v3);

        return (v0 ^ v1) ^ (v2 ^ v3);
    }

    private static void sipRound(long v0, long v1, long v2, long v3) {
        v0 += v1;
        v1 = Long.rotateLeft(v1, 13);
        v1 ^= v0;
        v0 = Long.rotateLeft(v0, 32);
        v2 += v3;
        v3 = Long.rotateLeft(v3, 16);
        v3 ^= v2;
        v0 += v3;
        v3 = Long.rotateLeft(v3, 21);
        v3 ^= v0;
        v2 += v1;
        v1 = Long.rotateLeft(v1, 17);
        v1 ^= v2;
        v2 = Long.rotateLeft(v2, 32);
    }
}
