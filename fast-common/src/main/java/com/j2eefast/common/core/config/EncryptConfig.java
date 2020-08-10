package com.j2eefast.common.core.config;

import com.j2eefast.common.core.crypto.MyEncryptablePropertyDetector;
import com.j2eefast.common.core.utils.ToolUtil;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>注册密钥</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-24 22:01
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Configuration
public class EncryptConfig {
    private final static Logger LOG                        = LoggerFactory.getLogger(EncryptConfig.class);

//    static {
//        //----------密码解密需要的Key---------------------------------------------
//        try {
//            ToolUtil.getFastServerInfos();
//        } catch (Exception e) {
//            LOG.error("获取机器码失败!",e);
//        }
//        //----------------------------------------------------------------------
//    }
    @Bean(name = "encryptablePropertyDetector")
    public EncryptablePropertyDetector encryptablePropertyDetector() {
        return new MyEncryptablePropertyDetector();
    }

    @Bean(name = "encryptablePropertyResolver")
    public EncryptablePropertyResolver encryptablePropertyResolver() {
        return new MyEncryptablePropertyResolver();
    }

}
