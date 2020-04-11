package com.j2eefast.common.core.license;

import cn.hutool.core.util.CharsetUtil;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.utils.ToolUtil;
import de.schlichtherle.license.*;
import de.schlichtherle.xml.GenericCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>自定义LicenseManager，用于增加额外的服务器硬件信息校验</p>
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2020-03-17 09:14
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public class CustomLicenseManager  extends LicenseManager {

    private static Logger                   LOG                     = LoggerFactory.getLogger(CustomLicenseManager.class);
    private static final int                DEFAULT_BUFSIZE         = 8 * 1024;


    public CustomLicenseManager() {

    }

    public CustomLicenseManager(LicenseParam param) {
        super(param);
    }


    /**
     * <p>
     *     校验当前服务器的IP地址是否在可被允许的IP范围内<br/>
     *     如果存在IP在可被允许的IP地址范围内，则返回true
     * </p>
     * @param expectedList 证书允许范围
     * @param serverList 服务器自身IP
     * @return
     */
    private boolean checkIpAddress(List<String> expectedList, List<String> serverList){

        if(expectedList != null && expectedList.size() > 0){
            if(serverList != null && serverList.size() > 0){
                for(String expected : expectedList){
                    if(serverList.contains(expected.trim())){
                        return true;
                    }
                }
            }
            return false;
        }else {
            return true;
        }
    }

    /**
     * <p>重写install方法</p>
     */
    @Override
    protected synchronized LicenseContent install(
            final byte[] key,
            final LicenseNotary notary)
            throws Exception {

        final GenericCertificate certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent)this.load(certificate.getEncoded());
        // 增加额外的自己的license校验方法，校验 机器码等
        this.validate(content);
        setLicenseKey(key);
        setCertificate(certificate);
        return content;
    }


    /**
     * <p>重写validate方法，增加机器码与IP校验</p>
     * */
    @Override
    protected synchronized void validate(final LicenseContent content)
            throws LicenseContentException {

        //1、 首先调用父类的validate方法
        super.validate(content);

        //2、 然后校验自定义的License参数
        //License中可被允许的参数信息
        LicenseCheck expectedCheck = (LicenseCheck) content.getExtra();

        if(ToolUtil.isNotEmpty(expectedCheck) && ToolUtil.isNotEmpty(ConfigConstant.FAST_OS_SN )){

            if(ToolUtil.isNotEmpty(expectedCheck.getFastSn())){
                if(!expectedCheck.getFastSn().toUpperCase().equals(ConfigConstant.FAST_OS_SN.toUpperCase())){
                    LOG.error("证书无效，当前服务器的注册码未激活!");
                    throw new LicenseContentException("证书无效，当前服务器的注册码未激活!");
                }
            }else{
                LOG.error("证书无效，当前服务器的注册码未激活!");
                throw new LicenseContentException("证书无效，当前服务器的注册码未激活!");
            }

            //校验IP地址
            if(expectedCheck.isIpCheck() && !checkIpAddress(expectedCheck.getIpAddress(),ConfigConstant.FAST_IPS)){
                LOG.error("证书无效，当前服务器的IP没在授权范围内"+expectedCheck.getIpAddress()+"");
                throw new LicenseContentException("证书无效，当前服务器的IP没在授权范围内");
            }
        }else{
            LOG.error("证书无效或不能获取服务器硬件信息");
            throw new LicenseContentException("证书无效或不能获取服务器硬件信息");
        }
    }

    /**
     * <p>重写verify方法</p>
     */
    @Override
    protected synchronized LicenseContent verify(final LicenseNotary notary)
            throws Exception {

        // Load license key from preferences,
        final byte[] key = getLicenseKey();
        if (null == key){
            throw new NoLicenseInstalledException(getLicenseParam().getSubject());
        }

        GenericCertificate certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent)this.load(certificate.getEncoded());
        // 增加额外的自己的license校验方法，校验 机器码等
        this.validate(content);
        setCertificate(certificate);
        return content;
    }

    /**
     * <p>重写XMLDecoder解析XML</p>
     */
    private Object load(String encoded){

        BufferedInputStream inputStream = null;
        XMLDecoder decoder = null;
        try {
            inputStream = new BufferedInputStream(new ByteArrayInputStream(encoded.getBytes(CharsetUtil.UTF_8)));
            decoder = new XMLDecoder(new BufferedInputStream(inputStream, DEFAULT_BUFSIZE),null,null);
            return decoder.readObject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            try {
                if(decoder != null){
                    decoder.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (Exception e) {
                LOG.error("XMLDecoder解析XML失败",e);
            }
        }
        return null;

    }
}
