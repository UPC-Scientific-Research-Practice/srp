package com.upc.srp.utils;

import sun.misc.BASE64Encoder;

import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;

/**
 * @author by zhoutao
 * @description TODO
 * @date 2019/12/26 16:27
 */
public class AuthUtil {

    public static final String ALGORITHM = "RSA";

    private static PublicKey publicKey;
    private static PrivateKey privateKey;

    /**
     * @description 生成盐值
     * @return string
     * @author zhoutao
     * @date 2019/12/26 16:34
     */
    public static String getSalt() {
        int salt = new Random().nextInt(99999999);
        return String.format("%08d", salt);
    }

    /**
     * @description 生成rsa密钥对
     * @author zhoutao
     * @date 2019/12/26 16:38
     */
    synchronized public static void generateKey() {
        if(publicKey==null){
            try {
                final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
                keyGen.initialize(1024);
                final KeyPair key = keyGen.generateKeyPair();

                publicKey = key.getPublic();
                privateKey = key.getPrivate();

                System.out.println("[public key]: "+publicKey);
                System.out.println("[private key]: "+privateKey);

            } catch (Exception e) {
                e.toString();
            }
        }
    }

    /**
     * @description 加密
     * @param text, key
     * @return byte
     * @author zhoutao
     * @date 2019/12/26 16:38
     */
    public static byte[] encrypt(String text) {
        byte[] cipherText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipherText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
            e.toString();
        }
        return cipherText;
    }

    /**
     * @description 解密
     * @param text
     * @return string
     * @author zhoutao
     * @date 2019/12/26 16:43
     */
    public static String decrypt(byte[] text) {
        String result = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decrypt = cipher.doFinal(text);
            result = new String(decrypt);
        } catch (Exception e) {
            throw new RuntimeException("请检查密码是否正确");
        }
        return result;
    }

    /**
     * @description 获取公钥
     * @return string
     * @author zhoutao
     * @date 2019/12/26 16:39
     */
    synchronized  public static String getKeyString() {
        generateKey();
        byte[] keyBytes = publicKey.getEncoded();
        return (new BASE64Encoder()).encode(keyBytes);
    }
}
