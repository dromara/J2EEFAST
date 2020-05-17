package com.fast.test;

import cn.hutool.core.util.StrUtil;
import cn.hutool.system.oshi.OshiUtil;
import org.junit.Test;
import oshi.hardware.NetworkIF;

import java.util.Scanner;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2019-04-04 10:39
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class TestC {
	@Test
	public void test() throws Exception {
//		String s = EnctryptTools.SM4Mac(ConfigConstant.FAST_MAC_KEY,"123123123123123".getBytes());
//		System.err.println(s);
//		String a = EnctryptTools.SM4Mac(ConfigConstant.FAST_MAC_KEY,"123123123123122".getBytes());
//		System.err.println(a);

		//System.err.println(HexUtil.convertByteArrayToHexStr(Base64.decode("qjPmSFxFAvHOLreP4cbDwA==")));
//		System.err.println(ShiroUtils.sha256Encrypt("admin","9rAjzhlpiE4cKEzJyy85"));
//		Long[] a = {12312312423L,12312312L};
//
//		List<Long> s = Arrays.asList(a);
//
//		System.err.println(s.toString());
//		boolean flag = false;
//		String[] s = StrUtil.split("192.168.1.127,192.168.2.23",",");
//		for(String c: s){
//			if(StrUtil.trimToEmpty(c).equalsIgnoreCase("192.168.1.12")){
//				flag = true;
//				break;
//			}
//
//		}
		System.err.println(getMainBoardSerial());
//		System.err.println(ToolUtil.getProjectPath());
//		SystemInfo sysInfo = new SystemInfo();
//		String s = sysInfo.getHardware().getComputerSystem().getSerialNumber();
		System.out.println(OshiUtil.getSystem().getSerialNumber());
		NetworkIF[] s = OshiUtil.getHardware().getNetworkIFs();
		for(NetworkIF n: s){
			System.out.println(n.getMacaddr() + "-- IP:"+StrUtil.join(",",n.getIPv4addr()));
		}
		System.out.println(OshiUtil.getHardware().getNetworkIFs());
		System.out.println(OshiUtil.getProcessor().getProcessorID());

	}

	public String getMainBoardSerial() throws Exception {
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
