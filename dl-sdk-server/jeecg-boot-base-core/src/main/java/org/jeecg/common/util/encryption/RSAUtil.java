package org.jeecg.common.util.encryption;

import com.alibaba.druid.util.StringUtils;;
import com.xkcoding.http.util.StringUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.common.util.encryption
 * @className: RSAUtil
 * @author: Eric
 * @description: TODO
 * @date: 2022/12/22 11:10
 * @version: 1.0
 */
public class RSAUtil {
    /**
     * 私钥签名方法
     * @param plainText 签名内容
     * @param privateKey 私钥
     * @return
     * @return java.lang.String
     **/
    public static String sign(String plainText, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(java.util.Base64.getDecoder().decode(privateKey));
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


}
