package com.j2eefast.common.core.license;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.j2eefast.common.core.constants.ConfigConstant;
import de.schlichtherle.license.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;

/**
 * <p>License校验类</p>
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2020-03-17 17:10
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public class LicenseVerify {

    private final static Logger                     LOG                        = LoggerFactory.getLogger(LicenseVerify.class);

    /**
     * <p>安装License证书</p>
     */
    public synchronized LicenseContent install(LicenseVerifyParam param) throws Exception {

        LicenseContent result = null;
        //1. 安装证书
        LicenseManager licenseManager =new CustomLicenseManager(initLicenseParam(param));
        // 先卸载证书 == 给null
        licenseManager.uninstall();
        // 安装
        result = licenseManager.install(FileUtil.file(param.getLicensePath()));

        ConfigConstant.AUTHORIZATION_TIME = StrUtil.format("{} - {}",DatePattern.NORM_DATETIME_FORMAT.format(result.getNotBefore()),DatePattern.NORM_DATETIME_FORMAT.format(result.getNotAfter()));

        LOG.info(MessageFormat.format("证书安装成功，证书有效期：{0} - {1}",DatePattern.NORM_DATETIME_FORMAT.format(result.getNotBefore()),
                DatePattern.NORM_DATETIME_FORMAT.format(result.getNotAfter())));

        return result;
    }

    /**
     * <p>初始化证书生成参数</p>
     * @param param License校验类需要的参数
     */
    private LicenseParam initLicenseParam(LicenseVerifyParam param){

        Preferences preferences = Preferences.userNodeForPackage(LicenseVerify.class);
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());
        KeyStoreParam publicStoreParam = new CustomKeyStoreParam(LicenseVerify.class
                ,param.getPublicKeysStorePath()
                ,param.getPublicAlias()
                ,param.getStorePass()
                ,null);

        return new DefaultLicenseParam(param.getSubject()
                ,preferences
                ,publicStoreParam
                ,cipherParam);
    }


    /**
     * <p>校验License证书</p>
     */
    public boolean verify(LicenseVerifyParam param){

        LicenseManager licenseManager = new CustomLicenseManager(initLicenseParam(param));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //2. 校验证书
        try {
            LicenseContent licenseContent = licenseManager.verify();
            LOG.info(MessageFormat.format("证书校验通过，证书有效期：{0} - {1}",format.format(licenseContent.getNotBefore()),format.format(licenseContent.getNotAfter())));
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
