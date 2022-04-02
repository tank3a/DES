import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class DES {

    public static final int A = 10;
    public static final int B = 11;
    public static final int C = 12;
    public static final int D = 13;
    public static final int E = 14;
    public static final int F = 15;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        //Key value must be 56-bit String
        //Data block must be 64-bit String

        //block and key is converted to 10-digit Integer
        long block, key, roundBlock, roundKey, result;

        CheckInput checkInput = new CheckInput();
        block = checkInput.check("Block");
        key = checkInput.check("Key");

        //init
        KeyGenerator keyGenerator = new KeyGenerator(key);
        BlockIp blockIp = new BlockIp();
        Round round = null;
        roundBlock = blockIp.createBlock(block, true);


        //round
        for(int i = 1; i <= 16; i++) {
            round = new Round(roundBlock);
            keyGenerator.bitShift(i);
            roundKey = keyGenerator.getCompressedKey();
            round.roundEP(roundKey);
            roundBlock = round.getBlock();
        }

        //end
        round.swap();

        roundBlock = round.getBlock();
        result = blockIp.createBlock(roundBlock, false);

        System.out.println("암호화된 결과: " + Long.toHexString(result).toUpperCase());
    }

}
