package utils;



import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;


public class SessionUtil {

    public  String createSessionId(String username){
//        SimpleDateFormat f_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = f_date.format(new Date());
        String timestamp = String.valueOf(System.currentTimeMillis());
            String OriginID = username+":"+timestamp;
                String EncodedID =getMD5(OriginID)+":xczzz";
        byte[] IDbytes = new byte[0];
        try {
            IDbytes = EncodedID.getBytes("utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String SessionID = Base64.encodeBase64String(IDbytes);



        return SessionID;
    }

    private static String decodeSessionId(String SessionID){
        String secondEncodedStr = "";
        byte[] IDbytes = Base64.decodeBase64(SessionID);
        try {
             secondEncodedStr = new String(IDbytes,"utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        System.out.println(secondEncodedStr);
        String md5str = secondEncodedStr.replace(":xczzz","");
//        System.out.println(md5str);
        String OriginID = convertMD5(convertMD5(md5str));
        return OriginID;
    }

    private static String getMD5(String str) {
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    private static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }




//    public static void main(String[] args) {
//        String str = createSessionId("zzz");
////        System.out.println(str);
////        System.out.println(decodeSessionId(str));
//    }
}



