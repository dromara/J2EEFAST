package com.j2eefast.common.core.license;

import lombok.Data;

/**
 * <p>License校验类需要的参数</p>
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2020-03-16 17:00
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
public class LicenseVerifyParam {
    /**
     * 证书subject
     */
    private String subject;

    /**
     * 公钥别称
     */
    private String publicAlias;

    /**
     * 访问公钥库的密码
     */
    private String storePass;

    /**
     * 证书生成路径
     */
    private String licensePath;

    /**
     * 密钥库存储路径
     */
    private String publicKeysStorePath;

    public LicenseVerifyParam() {
    }

    public LicenseVerifyParam(String subject, String publicAlias, String storePass,
                              String licensePath, String publicKeysStorePath) {
        this.subject = subject;
        this.publicAlias = publicAlias;
        this.storePass = storePass;
        this.licensePath = licensePath;
        this.publicKeysStorePath = publicKeysStorePath;
    }
}
