package com.j2eefast.common.core.config;

import cn.hutool.core.util.StrUtil;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.license.LicenseVerify;
import com.j2eefast.common.core.license.LicenseVerifyParam;
import com.j2eefast.common.core.utils.HexUtil;
import com.j2eefast.common.core.crypto.SM4;
import com.j2eefast.common.core.utils.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>启动安装证书</p>
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2020-03-16 17:32
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Component
public class LicenseCheckListener implements ApplicationListener<ContextRefreshedEvent> {

        private final static Logger                 LOG                        = LoggerFactory.getLogger(LicenseCheckListener.class);
        @Autowired
        private ApplicationContext applicationContext;
        /**
         * 证书subject
         */
        @Value("${license.subject: fastOs}")
        private String subject;

        /**
         * 公钥别称
         */
        @Value("${license.publicAlias: j2eefastCert}")
        private String publicAlias;

        /**
         * 访问公钥库的密码
         */
        @Value("${license.storePass: j2eefast.com}")
        private String storePass;

        /**
         * 证书生成路径
         */
        @Value("${license.licensePath: license.lic}")
        private String licensePath;

        /**
         * 密钥库存储路径
         */
        @Value("${license.publicKeysStorePath: publicCerts.keystore}")
        private String publicKeysStorePath;

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            //获取机器码
            try{
                ToolUtil.getFastServerInfos();

                //安装
                LOG.info("++++++++ 开始安装证书 ++++++++");

                LicenseVerifyParam param = this.getVerifyParam();

                LicenseVerify licenseVerify = new LicenseVerify();

                //安装证书
                licenseVerify.install(param);
                //验证证书唯一码是否有效
                LOG.info("++++++++ 证书安装结束 ++++++++");

            }catch (Exception e){
                LOG.error("安装证书异常:",e);
                ((ConfigurableApplicationContext) applicationContext).close();
            }
        }

        public  LicenseVerifyParam getVerifyParam(){
            LicenseVerifyParam param = new LicenseVerifyParam();
            param.setSubject(subject);
            param.setPublicAlias(publicAlias);
            param.setStorePass(storePass);
            param.setLicensePath(licensePath);
            param.setPublicKeysStorePath(publicKeysStorePath);
            return param;
        }

}
