package com.j2eefast.common.core.license.service;
import com.j2eefast.common.core.utils.ToolUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Linux服务器获取信息</p>
 * 若再linux 系统运行 获取不到硬件信息,请检查权限是否够,很大原因是权限问题
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2020-03-16 20:03
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public class LinuxServerInfos  extends  AbstractServerInfos{

    @Override
    protected List<String> getIpAddress() throws Exception {
        List<String> result = null;

        //获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();

        if(inetAddresses != null && inetAddresses.size() > 0){
            result = inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    protected String getMacAddress() throws Exception {
        String result = null;
        //1. 获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();

        if(inetAddresses != null && inetAddresses.size() > 0){
            //2. 获取所有网络接口的Mac地址
            result = String.join("#",inetAddresses.stream().map(this::getMacByInetAddress).distinct().collect(Collectors.toList()));
        }

        return result;
    }

    @Override
    protected String getCpcSerial() throws Exception {
        //序列号
        String serialNumber = "";

        //使用dmidecode命令获取CPU序列号
        String[] shell = {"/bin/bash","-c","dmidecode -t processor | grep 'ID' | awk -F ':' '{print $2}' | head -n 1"};
        Process process = Runtime.getRuntime().exec(shell);
        process.getOutputStream().close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line = reader.readLine().trim();
        if(ToolUtil.isNotEmpty(line)){
            serialNumber = line;
        }
        reader.close();
        return serialNumber;
    }

    @Override
    protected String getMainBoardSerial() throws Exception {
        //序列号
        String serialNumber = "";

        //使用dmidecode命令获取主板序列号
        String[] shell = {"/bin/bash","-c","dmidecode | grep 'Serial Number' | awk -F ':' '{print $2}' | head -n 1"};
        Process process = Runtime.getRuntime().exec(shell);
        process.getOutputStream().close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line = reader.readLine().trim();
        if(ToolUtil.isNotEmpty(line)){
            serialNumber = line;
        }

        reader.close();
        return serialNumber;
    }
}
