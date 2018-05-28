package spider.utils;

import org.apache.commons.codec.binary.Hex;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.math.BigInteger;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptTools {
    //AES加密
    public static String encrypt(String text, String secKey) throws Exception {
        byte[] raw = secKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // "算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
    //字符填充
    public static String zfill(String result, int n) {
        if (result.length() >= n) {
            result = result.substring(result.length() - n, result.length());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = n; i > result.length(); i--) {
                stringBuilder.append("0");
            }
            stringBuilder.append(result);
            result = stringBuilder.toString();
        }
        return result;
    }

    public void commentAPI() throws Exception {
        //私钥，随机16位字符串（自己可改）
        String secKey = "cd859f54539b24b7";
        String text = "{\"username\": \"\", \"rememberLogin\": \"true\", \"password\": \"\"}";
        String modulus = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
        String nonce = "0CoJUm6Qyw8W8jud";
        String pubKey = "010001";
        //2次AES加密，得到params
        String params = EncryptTools.encrypt(EncryptTools.encrypt(text, nonce), secKey);
        StringBuffer stringBuffer = new StringBuffer(secKey);
        //逆置私钥
        secKey = stringBuffer.reverse().toString();
        String hex = Hex.encodeHexString(secKey.getBytes());
        BigInteger bigInteger1 = new BigInteger(hex, 16);
        BigInteger bigInteger2 = new BigInteger(pubKey, 16);
        BigInteger bigInteger3 = new BigInteger(modulus, 16);
        //RSA加密计算
        BigInteger bigInteger4 = bigInteger1.pow(bigInteger2.intValue()).remainder(bigInteger3);
        String encSecKey= Hex.encodeHexString(bigInteger4.toByteArray());
        //字符填充
        encSecKey= EncryptTools.zfill(encSecKey, 256);
        //评论获取
        Document document = Jsoup.connect("http://music.163.com/weapi/v1/resource/comments/R_SO_4_437859519/").cookie("appver", "1.5.0.75771")
                .header("Referer", "http://music.163.com/").data("params", params).data("encSecKey", encSecKey)
                .ignoreContentType(true).post();
        System.out.println("评论：" + document.text());
    }

}
