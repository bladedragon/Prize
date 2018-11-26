package team.redrock.prize.Verify;


import java.util.Arrays;

public class CheckUtils {
    public static String token = "wxtoken";

    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = {token, timestamp, nonce};
        Arrays.sort(arr);
        StringBuffer sb = new StringBuffer();

        for (String s : arr) {
            sb.append(s);

        }
//    String temp = SHA1Utils.getSha1(sb.toString());
        String temp = SHA1Utils.encode(sb.toString());
        System.out.println(temp);
        return temp.equalsIgnoreCase(signature);
    }

    public static void main(String[] args) {

        if (checkSignature("95205b721b95f72a1b82b6551c3a40e658e18ceb", "1489498977", "1125364772")) {
            System.out.println("succcess");

        } else {
            System.out.println("false");
        }
    }
}
