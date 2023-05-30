package org.jeecg.modules.users.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * @version V1.0
 * @description: AES 加密解密工具
 * @author: xmh
 * @date: 2022/12/5 9:44
 */
public class AESUtils {

    /**
     * @param hexStr: 字符串
     * @return byte
     * @author xmh
     * @description 字符串转byte
     * @date 2022/12/15 13:46
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * @param msg:      加密信息
     * @param password: 23位的16进制key
     * @return String
     * @author xmh
     * @description aes-128-gcm 加密
     * @date 2022/12/5 16:27
     */
    public static String Encrypt(String msg, String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
            SecretKeySpec skey = new SecretKeySpec(hexStringToByte(password), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            // 获取向量
            byte[] ivb = cipher.getIV();
            byte[] encodedByteArray = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
            byte[] message = new byte[ivb.length + encodedByteArray.length];
            System.arraycopy(ivb, 0, message, 0, ivb.length);
            System.arraycopy(encodedByteArray, 0, message, ivb.length, encodedByteArray.length);
            return Base64.getEncoder().encodeToString(message);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * @param str: 密钥
     * @return byte
     * @author xmh
     * @description 密钥转byte
     * @date 2022/12/15 13:49
     */
    private static byte[] hexStringToByte(String str) {
        byte[] baKeyword = new byte[str.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(str.substring(i * 2, i * 2 + 2),
                    16));
            } catch (Exception e) {
                // 建议自行调整为日志输出或抛出异常
                e.printStackTrace();
            }
        }
        return baKeyword;
    }

    /**
     * @param serect:   解密的数据
     * @param password: 密钥
     * @return String
     * @author xmh
     * @description aes-128-gcm 解密
     * @date 2022/12/5 16:28
     */
    public static String Decrypt(String serect, String password) {
        try {
            byte[] sSrc = Base64.getDecoder().decode(serect);
            byte[] sKey = AESUtils.parseHexStr2Byte(password);

            GCMParameterSpec iv = new GCMParameterSpec(128, sSrc, 0, 12);
            System.out.println(Arrays.toString(iv.getIV()));
            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
            SecretKey key2 = new SecretKeySpec(sKey, "AES");

            cipher.init(Cipher.DECRYPT_MODE, key2, iv);

            //这边和nodejs不同的一点是 不需要移除后面的16位
            byte[] decryptData = cipher.doFinal(sSrc, 12, sSrc.length - 12);

            return new String(decryptData);
        } catch (Exception ex) {
            return null;
        }
    }
}
