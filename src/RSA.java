import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RSA {
    private final static BigInteger one = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();

    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modulus;

    // generate an N-bit (roughly) public and private key
    RSA(int N) {
        BigInteger p = BigInteger.probablePrime(8, random);
        BigInteger q = BigInteger.probablePrime(8, random);
//        65537
//        8473
//        32881
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus = p.multiply(q);
        publicKey = new BigInteger("201899");     // common value in practice = 2^16 + 1
        privateKey = publicKey.modInverse(phi);
    }


    BigInteger encrypt(BigInteger message) {
        return message.modPow(publicKey, modulus);
    }

    BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(privateKey, modulus);
    }

    public String toString() {
        String s = "";
        s += "public  = " + publicKey + "\n";
        s += "private = " + privateKey + "\n";
        s += "modulus = " + modulus;
        return s;
    }

    public static void main(String[] args) {
        int N = 7;
        RSA key = new RSA(N);
        System.out.println(key);

        // create random message, encrypt and decrypt


        //// create message by converting string to integer
        // String s = "test";
        // byte[] bytes = s.getBytes();
        // BigInteger message = new BigInteger(bytes);
        Map<String, String> map = new HashMap<>();
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<Integer> list = new ArrayList<>();
        for (int i = 65; i <= 90; i++) {
            list.add(i);

        }
        for (int i = 0; i < 26; i++) {
            String c = String.valueOf(alpha.charAt(i));
            map.put(String.valueOf(list.get(i)), c);
            System.out.println(map.get(String.valueOf(list.get(i))));
        }
        String name = "MYNAMEISMOHAMMEDELAMIN";
        List<String> list1 = new ArrayList<>();
        List<Byte> byteList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {

            int a = name.charAt(i);
            BigInteger message = new BigInteger(String.valueOf((int) a));
            BigInteger encrypt = key.encrypt(message);
            list1.add(encrypt.toString());
            BigInteger decrypt = key.decrypt(encrypt);
            byte b = encrypt.byteValue();
            byteList.add(b);

            stringBuilder.append(encrypt.toString());
//            System.out.println("message   = " +map.get(message.toString()));
            System.out.println("dec : = " + encrypt);

//            System.out.println();
        }
        String message = "";
        for (int i = 0; i < list1.size(); i++) {
            BigInteger bigInteger = key.decrypt(BigInteger.valueOf(Integer.parseInt(list1.get(i))));
            message += map.get(bigInteger.toString());
        }


//

//        convert message for hex
      StringBuilder sb = new StringBuilder();
        for (Byte b : byteList) {
            sb.append(Integer.toString((b & 0377) + 0x100, 16).substring(1));

        }
        System.out.println(message);
        System.out.println(sb);



    }
}