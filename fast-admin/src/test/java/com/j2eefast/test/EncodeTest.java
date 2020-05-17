package com.j2eefast.test;

import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.crypto.EnctryptTools;
import com.j2eefast.common.core.utils.JasyptUtils;
import com.j2eefast.common.core.utils.ToolUtil;
import org.junit.Test;

/**
 * <p>数据加密测试</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-25 09:11
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class EncodeTest {
    /**
     * 此类应当在正式环境删除,应与项目分离
     */
    static {
        //----------密码解密需要的Key---------------------------------------------
//        try {
//            ToolUtil.getFastServerInfos();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //----------------------------------------------------------------------
    }

    /**
     * 如果配置文件fast.encrypt.key 不配置密钥Key情况,通过明文获取配置文件存放密文
     */
    //ENC{}
    @Test
    public void encTest(){
        //例如数据库密码是 123456
        String pasw = "123456";
        String cipher = JasyptUtils.encryptPwd(pasw, ConfigConstant.KEY);
        System.out.println("ENC密文:" + cipher + "   yml文件写法:ENC{"+cipher+"}");
    }

    /**
     * DES加密
     * @throws Exception
     */
    @Test
    public void desTest() throws Exception {
        //例如数据库密码是 123456
        String pasw = "123456";
        String cipher = EnctryptTools.DesEncode(pasw, ConfigConstant.KEY);
        System.out.println("DES密文:" + cipher + "   yml文件写法:DES{"+cipher+"}");
    }

    /**
     * SM4 加密
     * @throws Exception
     */
    @Test
    public void sm4Test() throws Exception {
        //例如数据库密码是 123456
        String pasw = "123456";
        String cipher = EnctryptTools.SM4Encode(pasw, ConfigConstant.KEY);
        System.out.println("SM4密文:" + cipher + "   yml文件写法:SM4{"+cipher+"}");
    }

    //--------------------------------------------------------------------------------------
    /**
     * 如果配置文件fast.encrypt.key 例如key 设置 123456789
     */
    //ENC{}
    @Test
    public void encKeyTest(){
        //例如数据库密码是 123456
        //
        String pasw = "123456";
        String cipher = JasyptUtils.encryptPwd(pasw, "123456789");
        System.out.println("ENC密文:" + cipher + "   yml文件写法:ENC{"+cipher+"}");
    }

    /**
     * DES加密
     * @throws Exception
     */
    @Test
    public void desKeyTest() throws Exception {
        //例如数据库密码是 123456
        String pasw = "123456";
        String cipher = EnctryptTools.DesEncode(pasw, "123456789");
        System.out.println("DES密文:" + cipher + "   yml文件写法:DES{"+cipher+"}");
    }

    /**
     * SM4 加密
     * @throws Exception
     */
    @Test
    public void sm4KeyTest() throws Exception {
        //例如数据库密码是 123456
        String pasw = "123456";
        String cipher = EnctryptTools.SM4Encode(pasw, "123456789");
        System.out.println("SM4密文:" + cipher + "   yml文件写法:SM4{"+cipher+"}");
    }

}
