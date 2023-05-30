package org.jeecg.modules.game.test;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.util.Md5Util;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * @Author lili
 * @Description 游戏生成参数测试
 * @Date 14:19 2022/12/16
 **/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = JeecgSystemApplication.class)
public class GameConfTest {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    // 签名内容
    public static String plainText = "771$$idea$$33$$idea3$$2004$$68元内容自选礼包$$android$$167077466914103771#2004#1670774669068.000202212120004311410306277339450616707746796f3c79598d9e315f0cdde15cdc9642268be94fb54e24015a9332cc0ef8abd22a";

    /**
     * 加签验签
     */
    public static void main(String... args) throws Exception {
        //1.获取算法MD5实例
        MessageDigest md = MessageDigest.getInstance("MD5");
        Long time = new Date().getTime() * 1000;
        String msg = time + "460ede31ed26deb466517b003c87ba20";
        //2.MD5加密
        byte[] buff = md.digest(msg.getBytes());
        //3.将128位的二进制编码转为32位的16进制编码
        String gameKey = Md5Util.byteArrayToHexString(buff);
        System.out.println("gameKey:" + gameKey);
        //生成密钥对
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        //得到私钥
        System.out.println ("-----BEGIN PRIVATE KEY-----");
        String payPrikey = Base64.getEncoder().encodeToString(kp.getPrivate().getEncoded());
        System.out.println (payPrikey);
        System.out.println ("-----END PRIVATE KEY-----");
        System.out.println ("-----BEGIN PUBLIC KEY-----");
        //得到公钥
        String payPubkey = Base64.getEncoder().encodeToString( kp.getPublic().getEncoded());
        System.out.println (payPubkey);
        System.out.println ("-----END PUBLIC KEY-----");

        // RSA加密
        String data = "你好，世界！！！abc";

        String encryptData = privateKeyEncrypt(data, payPrikey);
        System.out.println("加密后内容:" + encryptData);

        // RSA解密
        String decryptData = publicKeyDecrypt(encryptData,payPubkey);
        System.out.println("解密后内容:" + decryptData);
        // 签名
        String result = sign(plainText, payPrikey);
        // 验签
        log.info("验签结果:{}", verify(plainText, result, payPubkey));
    }

    /**
     * 私钥签名方法
     * @param plainText 签名内容
     * @param privateKey 私钥
     * @return
     * @return java.lang.String
     **/
    public static String sign(String plainText, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            // 这里签名使用的是 SHA1WithRSA
            Signature signature = Signature.getInstance("SHA1WithRSA");
            // 以私钥字符串初始化签名
            signature.initSign(priKey);
            // 将原始串注入签名中
            signature.update(plainText.getBytes());
            // 签名后的结果，以字符数组形式存在
            byte[] signed = signature.sign();
            // 签名后的结果,以base64编码形式存在
            return Base64.getEncoder().encodeToString(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 公钥验签验签方法
     * @param plainText 未被加密的内容
     * @param sign 加密后的内容
     * @param publicKey 公钥
     * @return 是否成功 成功返回true
     **/
    public static boolean verify(String plainText, String sign, String publicKey)
            throws Exception {
        PublicKey publicKey1;
        try {
            X509EncodedKeySpec keySpec =
                    new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey1 = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return false;
        }
        Signature publicSignature = Signature.getInstance("SHA1withRSA");
        publicSignature.initVerify(publicKey1);
        publicSignature.update(plainText.getBytes(Charset.forName("UTF-8")));
        byte[] signatureBytes = Base64.getDecoder().decode(sign);
        return publicSignature.verify(signatureBytes);
    }

    /**
     * RSA私钥加密
     *
     * @param str
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String privateKeyEncrypt(String str, String privateKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        PrivateKey priKey = KeyFactory.getInstance("RSA").
                generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes()));
        return outStr;
    }

    /**
     * RSA公钥解密
     *
     * @param str
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String publicKeyDecrypt(String str, String publicKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        PublicKey pubKey =  KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.getDecoder().decode(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA公钥加密
     *
     * @param data 待加密数据
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * RSA私钥解密
     *
     * @param data 待解密数据
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.getDecoder().decode(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 签名
     *
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign());
    }

}
