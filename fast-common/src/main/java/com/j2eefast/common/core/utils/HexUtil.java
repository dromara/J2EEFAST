package com.j2eefast.common.core.utils;

import java.math.BigInteger;

/**
 * 16进制转换
 * @author zhouzhou
 * @date 2020-03-13 10:27
 */
public class HexUtil extends cn.hutool.core.util.HexUtil{
	/**
	 * 将一个字节转换成十六进制字符串
	 * 一个字节8位,转换成16进制,
	 *  01010000
	 * @param src
	 *            要转换的字节
	 * @return 两个字节的十六制字符串
	 */
	 public static String convertByteToHexStr(byte src) {
		byte[] ret = new byte[2];
		ret[0] = (byte) (src >> 4 & 0x0F);
		ret[0] = (byte) (ret[0] > 9 ? (ret[0] - 10 + 'a') : (ret[0] + '0'));
		ret[1] = (byte) (src & 0x0F);
		ret[1] = (byte) (ret[1] > 9 ? (ret[1] - 10 + 'a') : (ret[1] + '0'));
		return new String(ret);
	}
	 
	public static byte[] Hex16ToBytes(byte[] data){
		int datalen = data.length;
		datalen += (16 - datalen % 16) % 16; //不足16位不足16位
		byte[] secdata = new byte[datalen];
		System.arraycopy(data, 0, secdata, 0, data.length);
		for (int i = data.length; i < datalen; i++)
		{
			secdata[i] = 0x00;
		}
		return secdata;
	}
	 
	 /**
		 * 将字节数组转换成十六进制字符串
		 * 
		 * @param srcByteArray
		 *            字节数组
		 * @return 十六进制字符串
		 */
	public static String convertByteArrayToHexStr(byte[] srcByteArray) {
		String sTemp = null;
		StringBuffer sOutLine = new StringBuffer();
		byte[] inByte = srcByteArray;
		for (int iSerie = 0; iSerie < inByte.length; iSerie++) {
			// System.out.println("inbyte"+iSerie+inByte[iSerie]);
			if (inByte[iSerie] < 0) {
				sTemp = Integer.toHexString(256 + inByte[iSerie]);
			} else {
				sTemp = Integer.toHexString(inByte[iSerie]);
			}
			if (sTemp.length() < 2) {
				sTemp = "0" + sTemp;
			}
			sTemp = sTemp.toUpperCase();
			sOutLine = sOutLine.append(sTemp);
		}
		return sOutLine.toString();
	}
	
	/**
	 * 将十六进制字符串转换成字节数组
	 * 
	 * @param srcHex
	 *            十六进制字符串
	 * @return 字节数组
	 */
	public static byte[] convertHexStrToByteArray(String srcHex) {
		byte[] retByteArray = new byte[srcHex.length() / 2];
		byte tmpByte;
		String sDecode;
		for (int i = 0; i < srcHex.length() / 2; i++) {
			sDecode = "0x" + srcHex.substring(2 * i, 2 * i + 2);
			tmpByte = Integer.decode(sDecode).byteValue();
			retByteArray[i] = tmpByte;
		}
		return retByteArray;
	}
	
	/** 
	 * @Description 将二进制字节数组 转换 16进制字符串
	 * @Author zhouzhou Email:loveingowp@163.com
	 * @Date 2015-8-11
	 * @version
	 * @param src
	 * @param start
	 * @param end
	 * @return 
	 */
	 public static String bytesToHexStr(byte[] src,int start,int end){
        int len = src.length ;
        if(start<0 || start>=len ) {
        	start=0;
        } 
        if(end<=0 || end>len ) {
        	end = len;
        } 
        String ret = "";
        for(int i=start;i<end;i++) {
        	ret += convertByteToHexStr(src[i]);
        }
        return ret;
    }
	 
	 public static byte[] byteConvert32Bytes(BigInteger n){
			byte tmpd[] = (byte[])null;
			if(n == null){
				return null;
			}

			if(n.toByteArray().length == 33){
				tmpd = new byte[32];
				System.arraycopy(n.toByteArray(), 1, tmpd, 0, 32);
			}
			else if(n.toByteArray().length == 32){
				tmpd = n.toByteArray();
			}
			else{
				tmpd = new byte[32];
				for(int i = 0; i < 32 - n.toByteArray().length; i++){
					tmpd[i] = 0;
				}
				System.arraycopy(n.toByteArray(), 0, tmpd, 32 - n.toByteArray().length, n.toByteArray().length);
			}
			return tmpd;
	}
	 
	 /**
		 *  拷贝字节数组部分数据
		 * @param input 需要拷贝的数据
		 * @param startIndex 拷贝位置
		 * @param length 拷贝长度
		 * @return 拷贝成功数据
		 */
		public static byte[] subByte(byte[] input, int startIndex, int length) {
			byte[] bt = new byte[length];
			for (int i = 0; i < length; i++) {
				bt[i] = input[(i + startIndex)];
			}
			return bt;
		}


		/**
		 * 拼接字三个节数组
		 * @param a 需要拼接的数组 a
		 * @param b 需要拼接的数组 b
		 * @param c 需要拼接的数组 c
		 * @return a+b+c 数组
		 */
		public static byte[] pinJie3(byte[] a, byte[] b, byte[] c)
		{
			byte[] data = new byte[a.length + b.length + c.length];
			System.arraycopy(a, 0, data, 0, a.length);
			System.arraycopy(b, 0, data, a.length, b.length);
			System.arraycopy(c, 0, data, a.length + b.length, c.length);
			return data;
		}

		/**
		 *  拼接字两个节数组
		 * @param a 需要拼接的数组 a
		 * @param b 需要拼接的数组 b
		 * @return a+b 数组
		 */
		public static byte[] pinJie2(byte[] a, byte[] b)
		{
			byte[] data = new byte[a.length + b.length];
			System.arraycopy(a, 0, data, 0, a.length);
			System.arraycopy(b, 0, data, a.length, b.length);
			return data;
		}

		/**
		 *  int到byte[]  由低位到高位
		 * @param num
		 * @return
		 */
		public static byte[] intToBytes(int num)
		{
			byte[] bytes = new byte[4];
			bytes[0] = ((byte)(0xFF & num >> 0));
			bytes[1] = ((byte)(0xFF & num >> 8));
			bytes[2] = ((byte)(0xFF & num >> 16));
			bytes[3] = ((byte)(0xFF & num >> 24));
			return bytes;
		}

		/*******************************************************************
		 函数名称：byte[] intToBytes(byte[] src,int length)
		 函数功能：将整数转换成字节数组(低位在前，高位在后)
		 函数参数：src -- 要转换的字节数组，len -- 转换后的字节数组长度
		 函数返回：转换后的字节数组
		 异    常：无
		 项 目 组：自助服务系统研发组
		 作    者：
		 发布日期：20030920
		 修改日期：20030920
		 *******************************************************************/
		static public byte[] intToBytes(int src,int len){
			byte[] ret = new byte[len];
			for(int i=0;i<ret.length;i++) {
				ret[i] = (byte)(src >> (i*8) & 0xFF);
			}
			return ret;
		}
		
		/**
		 *  byte[]转int   由低位到高位
		 * @param bytes
		 * @return
		 */
		public static int byteToInt(byte[] bytes)
		{
			int num = 0;

			int temp = (0xFF & bytes[0]) << 0;
			num |= temp;
			temp = (0xFF & bytes[1]) << 8;
			num |= temp;
			temp = (0xFF & bytes[2]) << 16;
			num |= temp;
			temp = (0xFF & bytes[3]) << 24;
			num |= temp;
			return num;
		}
		public static int byteHexToInt(byte[] bytes)
		{
			int num = 0;
			int temp = (0xFF & bytes[0]) << 0;
			num |= temp;
			temp = (0xFF & bytes[1]) << 8;
			num |= temp;
			return num;
		}
		
		/**
		 * long 到byte[]  由低位到高位
		 * @param num
		 * @return
		 */
		public static byte[] longToBytes(long num)
		{
			byte[] bytes = new byte[8];
			for (int i = 0; i < 8; i++) {
				bytes[i] = ((byte)(int)(0xFF & num >> i * 8));
			}
			return bytes;
		}

		/**
		 * 不足16位倍数补足
		 * @param data
		 * @return
		 */
		public static byte[] BytesTo16K(byte[] data){
			int datalen = data.length;
			datalen += (16 - datalen % 16) % 16; //不足16位不足16位
			byte[] secdata = new byte[datalen];
			System.arraycopy(data, 0, secdata, 0, data.length);
			for (int i = data.length; i < datalen; i++)
			{
				secdata[i] = 32;
			}
			return secdata;
		}

	/**
	 * @author qianjf
	 * @return byte[]
	 * @param byte[] 功能：去掉byte数组最后的0x00
	 */

	public static byte[] trimLast0x00(byte[] src) {
		if (src == null) {
			return null;
		}

		byte dest[] = null;
		for (int i = src.length - 1; i > 0; i--) {
			if (src[i] == 0x00) {

				continue;
			} else {
				dest = new byte[i + 1];
				System.arraycopy(src, 0, dest, 0, i + 1);
				break;
			}

		}
		return dest;
	}
	
}
