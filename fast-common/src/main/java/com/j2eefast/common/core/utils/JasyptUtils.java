package com.j2eefast.common.core.utils;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
/**
 * <p></p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-23 14:29
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class JasyptUtils {
    /**
     * Jasypt生成加密结果
     *
     * @param key 设置的密钥
     * @param value    待加密值
     * @return
     */
    public static String encryptPwd( String value,String key) {
        PooledPBEStringEncryptor encryptOr = new PooledPBEStringEncryptor();
        encryptOr.setConfig(cryptOr(key));
        String result = encryptOr.encrypt(value);
        return result;
    }

    /**
     * 解密
     *
     * @param key 设置的密钥
     * @param value    待解密密文
     * @return
     */
    public static String decyptPwd(String value,String key) {
        PooledPBEStringEncryptor encryptOr = new PooledPBEStringEncryptor();
        encryptOr.setConfig(cryptOr(key));
        String result = encryptOr.decrypt(value);
        return result;
    }

    public static SimpleStringPBEConfig cryptOr(String password) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm(StandardPBEByteEncryptor.DEFAULT_ALGORITHM);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;
    }

    public static void main(String[] args) {
//        // 加密
        System.out.println(encryptPwd( "jdbc:mysql://192.168.20.110:3306/fastdb?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false","9c26b8"));
//        // 解密
        //System.out.println(decyptPwd("LhLNldGNz+UpcCIxk2jH3f2pHpFGaoMKVLHbxGNCmyuVjbEI23MSUkw0F0hWjSnj9FlzK16Nu8UDyQTCTKua2xEhZrGR93X7O64bBPB69RRpc8Nm2CS53xziJyGCzP/T00mxCkSln1k1qdjN4yaqNAgnIaKohUnqyc3hGC0rmfU=", "9c26b8bc19781e4cb2e0d"));
//        StringEncryptor encryptor = new StringEncryptor() {
//            @Override
//            public String encrypt(String s) {
//                return null;
//            }
//
//            @Override
//            public String decrypt(String s) {
//                return null;
//            }
//        };
    }
}
