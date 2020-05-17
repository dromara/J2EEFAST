package com.j2eefast.common.core.license.creat;

import com.j2eefast.common.core.license.LicenseCheck;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>许可证书生成类参数，注意若是正式项目则生成许可证书应该与项目分离</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2020-03-19 13:59
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
public class LicenseCreatorParam implements Serializable {

    private static final long serialVersionUID = -7793154252684580872L;
    /**
     * 证书subject
     */
    private String subject;

    /**
     * 私钥别称
     */
    private String privateAlias;

    /**
     * 私钥密码（需要妥善保管，不能让使用者知道）
     */
    private String keyPass;

    /**
     * 访问私钥库的密码
     */
    private String storePass;

    /**
     * 证书生成路径
     */
    private String licensePath;

    /**
     * 私钥库存储路径
     */
    private String privateKeysStorePath;

    /**
     * 证书生效时间
     */
    private Date issuedTime = new Date();

    /**
     * 证书失效时间
     */
    private Date expiryTime;

    /**
     * 用户类型
     */
    private String consumerType = "user";

    /**
     * 用户数量
     */
    private Integer consumerAmount = 1;

    /**
     * 描述信息
     */
    private String description = "";

    /**
     * 额外的服务器硬件校验信息
     */
    private LicenseCheck licenseCheck;

}
