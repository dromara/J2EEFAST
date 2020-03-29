package com.fast.common.core.license.creat;

import cn.hutool.core.io.FileUtil;
import com.fast.common.core.exception.RxcException;
import com.fast.common.core.license.CustomKeyStoreParam;
import com.fast.common.core.license.CustomLicenseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.schlichtherle.license.*;
import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

/**
 * <p>许可证书生成类,注意此类不能项目中应当与项目分离单独使用</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2020-03-19 14:03
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public class LicenseCreator {
    private final static X500Principal      DEFAULT_HOLDER_AND_ISSUER                   = new X500Principal("CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN");

    private static Logger                   LOG                                         = LoggerFactory.getLogger(LicenseCreator.class);

    private LicenseCreatorParam param;

    public LicenseCreator(LicenseCreatorParam param) {
        this.param = param;
    }

    /**
     * 生成License证书
     */
    public boolean generateLicense() {
        try {
            //无需校验许可证书
            LicenseManager licenseManager = new LicenseManager(initLicenseParam());
            LicenseContent licenseContent = initLicenseContent();
            licenseManager.store(licenseContent, FileUtil.file(param.getLicensePath()));
            return true;
        } catch (Exception e) {
            LOG.error(MessageFormat.format("证书生成失败：{0}", param), e);
            throw new RxcException(MessageFormat.format("证书生成失败：{0}", param));
        }
    }

    /**
     * 初始化证书生成参数
     */
    private LicenseParam initLicenseParam() {
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);

        //设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam privateStoreParam = new CustomKeyStoreParam(LicenseCreator.class
                , param.getPrivateKeysStorePath()
                , param.getPrivateAlias()
                , param.getStorePass()
                , param.getKeyPass());

        return new DefaultLicenseParam(param.getSubject()
                , preferences
                , privateStoreParam
                , cipherParam);
    }

    /**
     * 设置证书生成正文信息
     */
    private LicenseContent initLicenseContent() {

        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setIssuer(DEFAULT_HOLDER_AND_ISSUER);

        licenseContent.setSubject(param.getSubject());
        licenseContent.setIssued(param.getIssuedTime());
        licenseContent.setNotBefore(param.getIssuedTime());
        licenseContent.setNotAfter(param.getExpiryTime());
        licenseContent.setConsumerType(param.getConsumerType());
        licenseContent.setConsumerAmount(param.getConsumerAmount());
        licenseContent.setInfo(param.getDescription());

        //扩展校验，这里你也可以自定义一些额外的校验信息(也可以用json字符串保存)
        if (param.getLicenseCheck() != null) {
            licenseContent.setExtra(param.getLicenseCheck());
        }

        return licenseContent;
    }
}
