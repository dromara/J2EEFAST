package com.j2eefast.common.core.utils;

import cn.hutool.crypto.SecureUtil;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * <p>密码加密工具</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-01 21:56
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public class SaltUtil {

    /**
     * 获取盐值 长度为20
     * @return
     */
    public static String getRandomSalt(){
       return RandomStringUtils.randomAlphanumeric(20);
    }

    /**
     * SHA256加密 16次，带盐值
     */
    public static String sha256Encrypt(String password, String salt) {
        if (ToolUtil.isOneEmpty(password, salt)) {
            throw new IllegalArgumentException("密码或盐为空!");
        } else {
            String pass = password + salt;
            for(int i=0; i< 16; i++){
                pass =  SecureUtil.sha256(pass);
            }
            return pass;
        }
    }
}
