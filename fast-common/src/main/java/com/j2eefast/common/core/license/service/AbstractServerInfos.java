package com.j2eefast.common.core.license.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.system.SystemUtil;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.crypto.EnctryptTools;
import com.j2eefast.common.core.crypto.SM4;
import com.j2eefast.common.core.utils.HexUtil;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * <p>服务器硬件信息抽象类 -- 模板方法，将通用的方法抽离到父类中</p>
 *
 * @author appleyk
 * @version V.1.0.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 下午 1:24 2019-9-26
 */
public abstract class AbstractServerInfos {

    /**
     * 组装需要额外校验的License参数
     */
    public void getServerInfos() throws Exception {
        //获取系统参数组成机器唯一码
        String temp = this.getMacAddress() + this.getCpcSerial()+ this.getMainBoardSerial()
                + SystemUtil.getUserInfo().getCurrentDir() + SystemUtil.getOsInfo().getName() + SystemUtil.getHostInfo().getName()+
                SystemUtil.getOsInfo().getArch() + SystemUtil.getJavaInfo().getVersion();
        //再将机器码加密成16位字符串
        ConfigConstant.FAST_OS_SN = EnctryptTools.SM4Mac(ConfigConstant.FAST_MAC_KEY,temp.getBytes());
        ConfigConstant.KEY = EnctryptTools.SM4Mac(HexUtil.decodeHex(ConfigConstant.KEY),HexUtil.decodeHex(ConfigConstant.FAST_OS_SN));
        ConfigConstant.FAST_IPS = this.getIpAddress();
    }



    /**
     * 获取IP地址
     */
    protected abstract List<String> getIpAddress() throws Exception;

    /**
     * 获取Mac地址
     */
    protected abstract String getMacAddress() throws Exception;

    /**
     * <p>获取CPU序列号</p>
     */
    protected abstract String getCpcSerial() throws Exception;

    /**
     * <p>获取主板序列号</p>
     */
    protected abstract String getMainBoardSerial() throws Exception;

    /**
     * <p>获取当前服务器所有符合条件的InetAddress</p>
     */
    protected List<InetAddress> getLocalAllInetAddress() throws Exception {

        List<InetAddress> result = new ArrayList<>(4);

        // 遍历所有的网络接口
        for (Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface ni = (NetworkInterface) networkInterfaces.nextElement();
            // 在所有的接口下再遍历IP
            for (Enumeration addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                InetAddress address = (InetAddress) addresses.nextElement();
                //排除LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress类型的IP地址
                /*&& !inetAddr.isSiteLocalAddress()*/
                if(!address.isLoopbackAddress()
                        && !address.isLinkLocalAddress() && !address.isMulticastAddress()){
                    result.add(address);
                }
            }
        }
        return result;
    }

    /**
     * <p>获取某个网络地址对应的Mac地址</p>
     */
    protected String getMacByInetAddress(InetAddress inetAddr){

        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<mac.length;i++){
                if(i != 0) {
                    sb.append("-");
                }

                //将十六进制byte转化为字符串
                String temp = Integer.toHexString(mac[i] & 0xff);
                if(temp.length() == 1){
                    sb.append("0" + temp);
                }else{
                    sb.append(temp);
                }
            }
            return sb.toString().toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return null;
    }

}
