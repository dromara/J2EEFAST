package com.fast.common.core.license;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>自定义需要校验参数</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2020-03-17 22:43
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public class LicenseCheck implements Serializable {

    private static final long               serialVersionUID                = 1L;

    /**
     * 是否认证ip
     */
    private boolean isIpCheck ;

    /**
     * 可被允许的IP地址
     */
    private List<String> ipAddress;

    /**
     * FASTSN
     */
    public String fastSn;


    public boolean isIpCheck() {
        return isIpCheck;
    }

    public void setIpCheck(boolean ipCheck) {
        isIpCheck = ipCheck;
    }

    public List<String> getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(List<String> ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getFastSn() {
        return fastSn;
    }

    public void setFastSn(String fastSn) {
        this.fastSn = fastSn;
    }
}
