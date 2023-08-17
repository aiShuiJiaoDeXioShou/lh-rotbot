package linghe.rotbot.comm;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static String md5DigestAsHex(String input) {
        try {
            // 创建一个MessageDigest实例，使用MD5算法
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 对输入字符串进行编码
            byte[] bytes = input.getBytes(StandardCharsets.UTF_8);

            // 计算MD5值
            byte[] digest = md.digest(bytes);

            // 将字节数组转换为十六进制字符串
            BigInteger bigInt = new BigInteger(1, digest);
            StringBuilder hashText = new StringBuilder(bigInt.toString(16));

            // 补全32位长度
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }

            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
