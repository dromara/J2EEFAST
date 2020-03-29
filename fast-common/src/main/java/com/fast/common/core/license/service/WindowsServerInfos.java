package com.fast.common.core.license.service;

import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * <p>Windos服务器获取信息</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2020-03-16 20:42
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public class WindowsServerInfos  extends AbstractServerInfos{

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

        //使用WMIC获取CPU序列号
        Process process = Runtime.getRuntime().exec("wmic cpu get processorid");
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());

        if(scanner.hasNext()){
            scanner.next();
        }

        if(scanner.hasNext()){
            serialNumber = scanner.next().trim();
        }

        scanner.close();
        return serialNumber;
    }

    @Override
    protected String getMainBoardSerial() throws Exception {
        //序列号
        String serialNumber = "";

        //使用WMIC获取主板序列号
        Process process = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());

        if(scanner.hasNext()){
            scanner.next();
        }

        if(scanner.hasNext()){
            serialNumber = scanner.next().trim();
        }

        scanner.close();
        return serialNumber;
    }
}
