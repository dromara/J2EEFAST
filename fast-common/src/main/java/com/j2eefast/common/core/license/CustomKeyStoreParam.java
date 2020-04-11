package com.j2eefast.common.core.license;
import cn.hutool.core.io.FileUtil;
import de.schlichtherle.license.AbstractKeyStoreParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>自定义读取证书路径</p>
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2020-03-16 17:04
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public class CustomKeyStoreParam extends AbstractKeyStoreParam {

    /**
     * 公钥/私钥在磁盘上的存储路径
     */
    private String storePath;
    /**
     * 公钥别名
     */
    private String alias;
    /**
     *库密码
     */
    private String storePwd;
    /**
     *密钥密码
     */
    private String keyPwd;

    public CustomKeyStoreParam(Class clazz, String resource, String alias, String storePwd, String keyPwd) {
        super(clazz, resource);
        this.storePath = resource;
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }


    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getStorePwd() {
        return storePwd;
    }

    @Override
    public String getKeyPwd() {
        return keyPwd;
    }

    /**
     * AbstractKeyStoreParam里面的getStream()方法默认文件是存储的项目中。
     * 用于将公私钥存储文件存放到其他磁盘位置而不是项目中
     */
    @Override
    public InputStream getStream() throws IOException {
        return new FileInputStream( FileUtil.file(storePath));
    }
}
