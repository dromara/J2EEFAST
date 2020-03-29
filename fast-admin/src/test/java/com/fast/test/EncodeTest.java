package com.fast.test;

import com.fast.common.core.constants.ConfigConstant;
import com.fast.common.core.crypto.EnctryptTools;
import com.fast.common.core.utils.JasyptUtils;
import com.fast.common.core.utils.ToolUtil;
import org.junit.Test;

/**
 * <p>数据加密测试</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-25 09:11
 * @web: https://www.j2eefast.com
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
        // 106.13.137.180 ENC{urR5V11IElHFgy+t3uywNHROl+RtJMOk}
        // lixin@!redis ENC{kuhRFs667mFNaZ81ZijWl/MtYt3Ukfuo}
        //jdbc:mysql://47.104.227.152:3306/fastdb?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai  ENC{Ai9iFW9H7Ou35t9nYwyfUJ+gUMbmnuQ2DXILa+LUBSI1pn3/toZKnKyg1OdNLagBLilHQiKwnd1IwRbiFtcvcUStrhSbqmxDERyDTZoXI6oEH+sWHz9UnnA7rsSUBcqFtfYoyEfrwB7CkQUQrMQwEujKe9LwFWnl+iSPIlqLigIIha1QB8vELkSZveP50tytCw1OjWP1xBH4m+P50DMUlQ==}
        //fast ENC{2F3NbjB8f9kshoyCvyXeEw==}
        //fast@123$ ENC{PMmL0/9OHcXpMQyyiOY2aYi76r+1PFEa}
        String pasw = "fast@123$";
        String cipher = JasyptUtils.encryptPwd(pasw, "j2eefast@110.com");
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
        String pasw = "fast@123$";
        String cipher = EnctryptTools.SM4Encode(pasw, "123456789");
        System.out.println("SM4密文:" + cipher + "   yml文件写法:SM4{"+cipher+"}");
    }

}
