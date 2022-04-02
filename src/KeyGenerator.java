public class KeyGenerator {

    private long keyInput, keyValue, leftKey, rightKey;

    private final int[] keyIP = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };
    private final int[] shiftSchedule = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };
    private final int[] keyCompress = {
            14, 17, 11, 24, 1, 5, 3, 28,
            15, 6, 21, 10, 23, 19, 12, 4,
            26, 8, 16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55, 30, 40,
            51, 45, 33, 48, 44, 49, 39, 56,
            34, 53, 46, 42, 50, 36, 29, 32
    };

    KeyGenerator(long key) {
        this.keyInput = key;
        keyPermutation();
        divide();
    }

    //keyIP setting
    public void keyPermutation() {
        int i, keyIpIndex;
        keyIpIndex = 0;
        for(i = 1; i <= 64; i++) {
            if(i % 8 == 0)
                continue;

            keyValue += (keyInput>>(64 - keyIP[keyIpIndex]) & 1) << (55 - keyIpIndex);
            keyIpIndex++;
        }
    }

    public void divide() {
        leftKey = keyValue >>> 28;
        rightKey = keyValue ^ (leftKey<<28);
    }

    public void bitShift(int round) {
        int shift = shiftSchedule[round-1];

        leftKey = ((leftKey << shift) | (leftKey >>> (28-shift))) - (leftKey >>> (28-shift) << 28);
        rightKey = ((rightKey << shift) | (rightKey >>> (28-shift))) - (rightKey >>> (28-shift) << 28);
    }

    public long getCompressedKey() {
        long keyShiftPermute = (leftKey<<28) + rightKey;
        long compressedKey = 0;
        for(int i = 0; i < 48; i++) {
            compressedKey += (keyShiftPermute >>> (56 - keyCompress[i])) & 1;
            compressedKey = compressedKey << 1;
        }
        compressedKey = compressedKey >>> 1;
        return compressedKey;
    }
}
