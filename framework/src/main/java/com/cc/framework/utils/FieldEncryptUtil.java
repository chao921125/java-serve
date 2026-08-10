package com.cc.framework.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES-256-GCM 字段加密工具
 * <p>
 * 用于数据库敏感字段（手机号、身份证号、银行卡号等）的加密存储。
 * 使用认证加密模式 GCM，加密结果包含 IV 和认证标签，防篡改。
 * <p>
 * 密钥通过配置中心管理，支持密钥轮换。
 */
public final class FieldEncryptUtil {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;   // 96 bits
    private static final int GCM_TAG_LENGTH = 128;  // 128 bits

    /** 加密密钥（Base64 编码，32 字节 AES-256） */
    private static String encryptKey;

    private FieldEncryptUtil() {}

    /**
     * 设置加密密钥（从配置中心加载）
     */
    public static void setEncryptKey(String key) {
        encryptKey = key;
    }

    /**
     * 加密
     * @param plainText 明文
     * @return Base64 编码的密文（IV + 密文）
     */
    public static String encrypt(String plainText) {
        if (plainText == null || plainText.isEmpty()) return plainText;
        try {
            byte[] keyBytes = Base64.getDecoder().decode(encryptKey);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            byte[] iv = new byte[GCM_IV_LENGTH];
            new SecureRandom().nextBytes(iv);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

            byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            byte[] result = new byte[GCM_IV_LENGTH + cipherText.length];
            System.arraycopy(iv, 0, result, 0, GCM_IV_LENGTH);
            System.arraycopy(cipherText, 0, result, GCM_IV_LENGTH, cipherText.length);

            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException("字段加密失败", e);
        }
    }

    /**
     * 解密
     * @param cipherText Base64 编码的密文
     * @return 明文
     */
    public static String decrypt(String cipherText) {
        if (cipherText == null || cipherText.isEmpty()) return cipherText;
        try {
            byte[] keyBytes = Base64.getDecoder().decode(encryptKey);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            byte[] data = Base64.getDecoder().decode(cipherText);
            byte[] iv = new byte[GCM_IV_LENGTH];
            System.arraycopy(data, 0, iv, 0, GCM_IV_LENGTH);

            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

            byte[] plainText = cipher.doFinal(data, GCM_IV_LENGTH, data.length - GCM_IV_LENGTH);
            return new String(plainText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("字段解密失败", e);
        }
    }

    /**
     * 生成 256 位 AES 密钥（Base64 编码）
     */
    public static String generateKey() {
        byte[] key = new byte[32]; // 256 bits
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
