
package com.j2eefast.common.core.crypto;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j2eefast.common.core.utils.HexUtil;




public class EncryptUtil
{
	private static final Logger LOG = LoggerFactory.getLogger(EncryptUtil.class);
	public EncryptUtil()
	{}
	public static String getPwdBytes(byte pinFmt,byte [] pin,byte []accountNo)throws EncryptException
	{
		byte[] pinBlock;
		if (pinFmt == EncryptConst.ENCRY_PIN_ANSI98)
		{
			if (accountNo != null) {
				pinBlock = formatPinblockToPwd(accountNo, pin);
			} else {
				pinBlock = formatPIN(pin);
			}
		}
		else if (pinFmt == EncryptConst.ENCRY_PIN_ISO9564_1)
		{
			pinBlock = formatPIN9564(pin);
		}
		else
		{
			throw new EncryptException(
					"encryptPIN algorithm is invalid exception.");
		}
		StringBuffer pwd=new StringBuffer();
		for(int i=1;i<4;i++)
		{						
			pwd.append(HexUtil.convertByteToHexStr(pinBlock[i]));			
		}
		return pwd.toString();
	}
	
	
	/**
     * ������DES���ܵ���Կ
     * @param short keyLen Ҫ��ɵ���Կ����?��λ�ֽڣ�8/16/24
     * @return byte[] ������Կֵ
     * @throws EncryptException
     */
	public static byte[] generateKey(byte keyLen) throws EncryptException
	{
		try
		{
			if (keyLen != 8 && keyLen != 16 && keyLen != 24)
			{
				throw new EncryptException("Generate key length invalid Exception.");
			}
			Security.insertProviderAt(new com.sun.crypto.provider.SunJCE(), 1);
			String algorithm = "DES";
			if (keyLen > 8)
			{
				algorithm = "DESede";
			}
			KeyGenerator generator = KeyGenerator.getInstance(algorithm);
			generator.init(keyLen * 7, new SecureRandom());
			Key key = generator.generateKey();
			byte[] byteKey = key.getEncoded();
			if (keyLen == 16)
			{
				byte[] keyValue = new byte[16];
				System.arraycopy(byteKey, 0, keyValue, 0, 16);
				return keyValue;
			}
			else {
				return key.getEncoded();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new EncryptException("Generate encrypt key occurs Exception.");
		}
	}

	/**
     * �����Կֵ��ü��ܵ�Key����
     * @param keyValue ��Կֵ
     * @return Key ���ضԳ���Կ
     * @throws EncryptException
     */
	public static Key getKey(byte[] keyValue) throws EncryptException
	{
		try
		{
			if (keyValue.length != 8 && keyValue.length != 16 && keyValue.length != 24)
			{
				throw new EncryptException("Invalid key length exception");
			}
			SecretKey key = null;
			if (keyValue.length == 8)
			{
				key = new SecretKeySpec(keyValue, "DES");
			}
			else if (keyValue.length == 16)
			{
				byte[] tempKeyValue = new byte[24];
				System.arraycopy(keyValue, 0, tempKeyValue, 0, 16);
				System.arraycopy(keyValue, 0, tempKeyValue, 16, 8);
				key = new SecretKeySpec(tempKeyValue, "DESede");
			}
			else if (keyValue.length == 24)
			{
				key = new SecretKeySpec(keyValue, "DESede");
			}
			return key;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new EncryptException("Get key exception.");
		}
	}

	/**
     * ���ܷ���
     * @param mode 0-ΪDES ECB���� 1-ΪDES CBC���� 2-Ϊ3DES ECB���� 3-Ϊ3DES CBC����
     * @param keyValue ��Կֵ
     * @param data ��Ҫ���ܵ����?
     * @param initIV ��ʼ����������CBC���ܣ���Ϊ��Ĭ��Ϊ0
     * @return byte[] ���ܺ�����
     * @throws util.EncryptException
     */
	public static byte[] doEncrypt(byte mode, byte[] keyValue, byte[] data,
			byte[] initIV) throws EncryptException
	{
		if (keyValue == null || keyValue.equals("")) {
			throw new EncryptException("Encrypt key value cann't is null exception.");
		}
		if (data == null || data.equals("")) {
			throw new EncryptException("Encrypt data cann't is null exception.");
		}
		if (data.length % 8 != 0) {
			throw new EncryptException("Encrypt data length is invalid exception.");
		}
		try
		{
			String algorithm = "";
			if (mode == EncryptConst.ENCRY_DES_ECB)
			{
				algorithm = "DES/ECB/NoPadding";
			}
			else if (mode == EncryptConst.ENCRY_DES_CBC)
			{
				algorithm = "DES/CBC/NoPadding";
			}
			else if (mode == EncryptConst.ENCRY_3DES_ECB)
			{
				algorithm = "DESede/ECB/NoPadding";
			}
			else if (mode == EncryptConst.ENCRY_3DES_CBC)
			{
				algorithm = "DESede/CBC/NoPadding";
			}
			else
			{
				throw new EncryptException("Do encrypt mode invalid exception.");
			}
			Cipher cipher = Cipher.getInstance(algorithm);
			if (mode == EncryptConst.ENCRY_DES_CBC || mode == EncryptConst.ENCRY_3DES_CBC)
			{
				byte[] iv = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
				if (initIV != null)
				{
					int nLen = initIV.length;
					if (nLen > 8)
						nLen = 8;
					System.arraycopy(initIV, 0, iv, 0, nLen);
				}
				IvParameterSpec spec = new IvParameterSpec(iv);
				cipher.init(Cipher.ENCRYPT_MODE, getKey(keyValue), spec);
			}
			else {
				cipher.init(Cipher.ENCRYPT_MODE, getKey(keyValue));
			}
			byte[] raw = cipher.doFinal(data);
			return raw;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new EncryptException("Do encrypt occurs exception.");
		}
	}

	/**
     * ���ܷ���
     * @param mode 0-ΪDES ECB���� 1-ΪDES CBC���� 2-Ϊ3DES ECB���� 3-Ϊ3DES CBC����
     * @param keyValue ��Կֵ
     * @param raw ����ܵ����
     * @param initIV ��ʼ����������CBC���ܣ���Ϊ��Ĭ��Ϊ0
     * @return byte[] ���ܺ�����
     * @throws util.EncryptException
     */
	public static byte[] doDecrypt(byte mode, byte[] keyValue, byte[] raw,byte[] initIV) throws EncryptException
	{
		if (keyValue == null || keyValue.equals("")) {
			throw new EncryptException("Decrypt key value cann't is null exception.");
		}
		if (raw == null || raw.equals("")) {
			throw new EncryptException("Decrypt data cann't is null exception.");
		}
		if (raw.length % 8 != 0) {
			throw new EncryptException("Decrypt data length is invalid exception.");
		}
		try
		{
			String algorithm = "";
			if (mode == EncryptConst.DECRY_DES_ECB)
			{
				algorithm = "DES/ECB/NoPadding";
			}
			else if (mode == EncryptConst.DECRY_DES_CBC)
			{
				algorithm = "DES/CBC/NoPadding";
			}
			else if (mode == EncryptConst.DECRY_3DES_ECB)
			{
				algorithm = "DESede/ECB/NoPadding";
			}
			else if (mode == EncryptConst.DECRY_3DES_CBC)
			{
				algorithm = "DESede/CBC/NoPadding";
			}
			else
			{
				throw new EncryptException("Do encrypt mode invalid exception.");
			}
			Cipher cipher = Cipher.getInstance(algorithm);
			if (mode == EncryptConst.DECRY_DES_CBC || mode == EncryptConst.DECRY_3DES_CBC)
			{
				byte[] iv = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
				if (initIV != null)
				{
					int nLen = initIV.length;
					if (nLen > 8)
						nLen = 8;
					System.arraycopy(initIV, 0, iv, 0, nLen);
				}
				IvParameterSpec spec = new IvParameterSpec(iv);
				cipher.init(Cipher.DECRYPT_MODE, getKey(keyValue), spec);
			}
			else {
				cipher.init(Cipher.DECRYPT_MODE, getKey(keyValue));
			}
			byte[] data = cipher.doFinal(raw);
			return data;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new EncryptException("Do decrypt occurs Exception.");
		}
	}

	/**
     * @Description ANSI X9.9MAC�㷨  ��  ANSI X9.19 MAC  �㷨
     * @param mode 0-ANSI9.9 1-ANSI9.19
     * @param keyValue ��Կֵ
     * @param data �����MAC�����?
     * @param initIV ��ʼ������Ϊ��Ĭ��Ϊ0
     * @return byte[] ������MAC
     * @throws util.EncryptException
     * 1,mac,data,null
     */
	public static byte[] generateMac(byte mode, byte[] keyValue, byte[] data,byte[] initIV) throws EncryptException
	{
		//	System.out.println("keyValue="+HexUtil.convertByteArrayToHexStr(keyValue));
		//	System.out.println("data="+HexUtil.convertByteArrayToHexStr(data));
	
		if (keyValue == null || keyValue.equals("")) {
			throw new EncryptException("generateMac key value cann't is null exception.");
		}
		if (data == null || data.equals("")) {
			throw new EncryptException("generateMac data cann't is null exception.");
		}
		//if (mode != EncryptConst.ENCRY_MAC_ANSI99 && mode != EncryptConst.ENCRY_MAC_ANSI99)
		if (mode != EncryptConst.ENCRY_MAC_ANSI99 && mode != EncryptConst.ENCRY_MAC_ANSI919)
		{
			throw new EncryptException("generateMac mode is invalid exception.");
		}
		try
		{
			int datalen = data.length;
			datalen += (8 - datalen % 8) % 8;
			byte[] srcdata = new byte[datalen];
			System.arraycopy(data, 0, srcdata, 0, data.length);
			for (int i = data.length; i < datalen; i++)
			{
				srcdata[i] = 0x00;
			}

			byte[] key1 = new byte[8];
			byte[] key2 = new byte[8];
			byte[] key3 = new byte[8];
			if (keyValue.length == 8)
			{
				System.arraycopy(keyValue, 0, key1, 0, 8);
			}
			else if (keyValue.length == 16)
			{
				System.arraycopy(keyValue, 0, key1, 0, 8);
				System.arraycopy(keyValue, 8, key2, 0, 8);
				System.arraycopy(keyValue, 0, key3, 0, 8);
			}
			else if (keyValue.length == 24)
			{
				System.arraycopy(keyValue, 0, key1, 0, 8);
				System.arraycopy(keyValue, 8, key2, 0, 8);
				System.arraycopy(keyValue, 16, key3, 0, 8);
			}
			else
			{
				throw new EncryptException("Encrypt key length is invalid exception.");
			}
			Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
			byte[] iv = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
			if (initIV != null)
			{
				int nLen = initIV.length;
				if (nLen > 8)
					nLen = 8;
				System.arraycopy(initIV, 0, iv, 0, nLen);
			}
			IvParameterSpec spec = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, getKey(key1), spec);
			byte[] retData = cipher.doFinal(srcdata);
			byte[] tempData = new byte[8];
			System.arraycopy(retData, retData.length - 8, tempData, 0, 8);
			if (keyValue.length >= 16)
			{
				cipher = null;
				retData = null;
				cipher = Cipher.getInstance("DES/CBC/NoPadding");
				cipher.init(Cipher.DECRYPT_MODE, getKey(key2), spec);
				tempData = cipher.doFinal(tempData);
				cipher.init(Cipher.ENCRYPT_MODE, getKey(key3), spec);
				tempData = cipher.doFinal(tempData);
			}
			return tempData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new EncryptException("Do decrypt occurs Exception.");
		}
	}

	public static byte[] formatPIN(byte[] pin)
	{
		if (pin == null)
			pin = new byte[0];
		int nLen = pin.length;
		byte[] fmtPin = new byte[16];
		fmtPin[0] = '0';
		fmtPin[1] = (byte) Character
				.toUpperCase((Character.forDigit(nLen, 16)));
		for (int i = 2; i < 16; i++)
		{
			fmtPin[i] = 'F';
		}
		System.arraycopy(pin, 0, fmtPin, 2, nLen);
		return fmtPin;
	}
	
	 public static byte[] formatSM4PIN(byte[] pin)
	    {
	        if (pin == null)
	            pin = new byte[0];
	        int nLen = pin.length;
	        byte[] fmtPin = new byte[32];
	        fmtPin[0] = (byte)'0';

	        fmtPin[1] = (byte) Character.toUpperCase((Character.forDigit(nLen, 16)));
	        for (int i = 2; i < 32; i++)
	        {
	            fmtPin[i] = (byte)'F';
	        }
	        System.arraycopy(pin, 0, fmtPin, 2, nLen);
	        System.out.println("fmtPin:" + HexUtil.convertByteArrayToHexStr(fmtPin));
	        return fmtPin;
	    }
	
	public static byte[] formatPIN9564(byte[] pin)
	{
		if (pin == null)
			pin = new byte[0];
		int nLen = pin.length;
		byte[] fmtPin = new byte[16];
		fmtPin[0] = '1';
		fmtPin[1] = (byte) Character
				.toUpperCase((Character.forDigit(nLen, 16)));
		java.util.Random rand = new java.util.Random();
		for (int i = 2; i < 16; i++)
		{
			byte chRand = (byte) Character.toUpperCase((Character.forDigit(rand
					.nextInt(15), 16)));
			fmtPin[i] = chRand;
		}
		System.arraycopy(pin, 0, fmtPin, 2, nLen);
		return fmtPin;
	}

	public static byte[] formatAccountNo(byte[] accountNo)
	{
		byte[] accountNoPart = new byte[16];
		for (int i = 0; i < 16; i++)
			accountNoPart[i] = '0';
		if (accountNo.length > 12)
			System.arraycopy(accountNo, accountNo.length - 13, accountNoPart,
					4, 12);
		else {//不含最后一位校验位，不足12位左补0
			byte[] temp = new byte[12];
			for (int i = 0; i < 12; i++)
				temp[i] = '0';
			System.arraycopy(accountNo, 0, temp, 12-accountNo.length, accountNo.length);
			System.arraycopy(temp, 0, accountNoPart, 4, temp.length);
		}
		return accountNoPart;
	}
	
	/**
     * 
     *     SM4 计算  PAN
     *  
     * @author zhouzhou Email:loveingowp@163.com
     * @date Aug 19, 2018
     * @version 1.0.1
     * @param accountNo
     * @return
     *
     */
    public static byte[] formatSM4AccountNo(byte[] accountNo)
    {
        byte[] accountNoPart = new byte[32];
        for (int i = 0; i < 32; i++)
            accountNoPart[i] = (byte)'0';
        if (accountNo.length > 12)
        	System.arraycopy(accountNo, accountNo.length - 13, accountNoPart,
                    20, 12);
        else {
        	//不含最后一位校验位，不足12位左补0
			byte[] temp = new byte[12];
			for (int i = 0; i < 12; i++)
				temp[i] = '0';
			System.arraycopy(accountNo, 0, temp, 12-accountNo.length, accountNo.length);
			
			System.arraycopy(temp, 0, accountNoPart, 20, temp.length);
        }
        //System.arraycopy(accountNo, 0, accountNoPart, 20, accountNo.length);
        //string s = System.Text.Encoding.Default.GetString(accountNoPart);
        return accountNoPart;
    }
    
    /**
     * 
     *     SM4 计算PinBlock
     *  
     * @author zhouzhou Email:loveingowp@163.com
     * @date Aug 19, 2018
     * @version 1.0.1
     * @param accountNo 卡号
     * @param pin 密码明文
     * @return PinBlock
     *
     */
    public static byte[] formatSM4Pinblock(byte[] accountNo, byte[] pin)
    {
        byte[] accountNoPart = HexUtil.convertHexStrToByteArray(
                new String(formatSM4AccountNo(accountNo)));
        
        byte[] fmtPin = HexUtil.convertHexStrToByteArray(new String(
                formatSM4PIN(pin)));
//        String pin0 = Encoding.Default.GetString(
//                formatSM4PIN(pin));
        byte[] pinBlock = new byte[16];
        //System.out.println("--+:"+HexUtil.convertByteArrayToHexStr(accountNoPart));
        for (int i = 0; i < 16; i++) {
			pinBlock[i] = (byte)(accountNoPart[i] ^ fmtPin[i]);
		}
        return pinBlock;
    }
    
    /**
     *  SM4 反解PinBlock
     * @Description:TODO
     * @author zhouzhou 18774995071@163.com
     * @time 2019-05-24 11:05
     * @param accountNo 卡号
     * @param pin pin 密码密文
     * @return 密码明文
     *
     */
    public static byte[] PinblockSM4Pin(byte[] accountNo, byte[] pin)
    {
    	byte[] accountNoPart = HexUtil.convertHexStrToByteArray(new String(
    			formatSM4AccountNo(accountNo)));
		byte[] fmtPin = pin;
		byte[] pinBlock = new byte[16];
		for (int i = 0; i < 16; i++) {
			pinBlock[i] = (byte) (accountNoPart[i] ^ fmtPin[i]);
		}
		return pinBlock;
    }
    

	public static byte[] formatPinblock(byte[] accountNo, byte[] pin)
	{
		byte[] accountNoPart = HexUtil.convertHexStrToByteArray(new String(
				formatAccountNo(accountNo)));
		byte[] fmtPin = HexUtil.convertHexStrToByteArray(new String(
				formatPIN(pin)));
		byte[] pinBlock = new byte[8];
		for (int i = 0; i < 8; i++) {
			pinBlock[i] = (byte) (accountNoPart[i] ^ fmtPin[i]);
		}
		return pinBlock;
	}
	
	public static byte[] formatPinblockToPwd(byte[] accountNo, byte[] pin)
	{
		byte[] accountNoPart = HexUtil.convertHexStrToByteArray(new String(
				formatAccountNo(accountNo)));
		byte[] fmtPin = pin;
		byte[] pinBlock = new byte[8];
		for (int i = 0; i < 8; i++) {
			pinBlock[i] = (byte) (accountNoPart[i] ^ fmtPin[i]);
		}
		return pinBlock;
	}
	
	/**
	 * 
	 *     密码3DS解密
	 *  
	 * @author zhouzhou Email:loveingowp@163.com
	 * @date Aug 15, 2018
	 * @version 1.0.1
	 * @param key PINKEY
	 * @param accountNo 账号
	 * @param pass 密码密文
	 * @return
	 *
	 */
	public static String decryptPass(byte[] key,String accountNo,byte[] pass){
		String pin = "1";
//		if(keyValue.length() != 32 && pinKey.length() != 32 && (accountNo.length() == 15 || 
//				accountNo.length() == 19)){
//			return "1";
//		}
		byte[] JpinKey = null;
		byte[] pinBlock = null;
		//byte[] key = HexUtil.convertHexStrToByteArray(keyValue);
		//1. 解pinkey
		//2. 算出Pinblock
		try {
//			JpinKey = EncryptUtil.doDecrypt(EncryptConst.DECRY_DES_ECB,key, 
//					HexUtil.convertHexStrToByteArray(pinKey), null);
			pinBlock = EncryptUtil.doDecrypt(EncryptConst.DECRY_3DES_ECB,key, 
					pass, null);
		} catch (EncryptException e) {
			System.err.println("解pinkey算法报错");
			LOG.error("解pinkey算法报错",e);
		}
		
		//算出明文密码
		pin = HexUtil.convertByteArrayToHexStr(EncryptUtil.formatPinblockToPwd(
				accountNo.getBytes(), pinBlock)).toUpperCase().toString().substring(2, 8);
		return pin;
	}

	/**
     * ����PIN
     * @param pinFmt ����PINBLOCK�㷨 0-ΪANSI X9.8 1-ISO 9564-1 //2-IBM3624
     * @param keyValue ��Կֵ
     * @param raw ����ܵ����
     * @param initIV ��ʼ����������CBC���ܣ���Ϊ��Ĭ��Ϊ0
     * @return byte[] ���ܺ�����
     * @throws util.EncryptException
     */
	public static byte[] encryptPIN(byte pinFmt, byte[] keyValue,
			byte[] accountNo, byte[] pin) throws EncryptException
	{
		if (keyValue == null || keyValue.equals("")) {
			throw new EncryptException(
					"encryptPIN key value cann't is null exception.");
		}
		if (pin == null || pin.equals(""))
			throw new EncryptException(
					"encryptPIN pin cann't is null exception.");
		try
		{
			byte[] pinBlock;
			if (pinFmt == EncryptConst.ENCRY_PIN_ANSI98)
			{
				if (accountNo != null) {
					pinBlock = formatPinblock(accountNo, pin);
				} else {
					pinBlock = formatPIN(pin);
				}
			}
			else if (pinFmt == EncryptConst.ENCRY_PIN_ISO9564_1)
			{
				pinBlock = formatPIN9564(pin);
			}
			else
			{
				throw new EncryptException(
						"encryptPIN algorithm is invalid exception.");
			}
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, getKey(keyValue));
			byte[] retPinblock = cipher.doFinal(pinBlock);
			return retPinblock;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new EncryptException("encryptPIN occurs Exception.");
		}
	}
	
	
	
	/**
	 * 
	 * @Description：2倍Key3DES-ECB  带主账号PinBlock加密 -- 银行加密方法
	 *  
	 * @author zhouzhou Email:loveingowp@163.com
	 * @date Aug 15, 2018
	 * @version 1.0.1
	 * @param pinKey 主密钥
	 * @param accountNo 银行卡号
	 * @param pin 明文密码
	 * @return 返回 "1" 错误,返回通过3DES加密后的密码
	 *
	 */
	public static byte[] encryptPass(byte[] pinKey,String accountNo, String pin){
		byte[] pass = null;
		//2. 算出PinBlock
		byte[] pinBlockB = EncryptUtil.formatPinblock(accountNo.getBytes(), pin.getBytes());
		//3. 加密PinBlock
		try {
			pass = EncryptUtil.doEncrypt(EncryptConst.DECRY_3DES_ECB, pinKey, pinBlockB, null); 
		} catch (EncryptException e) {
			System.err.println("加密pinblock算法报错");
			LOG.error("加密pinblock算法报错",e);
		}
		return pass;
	}

	public static void main(String[] args)
	{
		try
		{
//			//byte[] key = { (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80,(byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80 };
//			String keyValue = "3786253EC8752C58899E490432B31F58";
//			byte[] key = HexUtil.convertHexStrToByteArray(keyValue);
//			//System.out.println(key.length);
//			byte[] mm = HexUtil.convertHexStrToByteArray("D12ED59D412E4D60");
//			byte[]  pinBlock = formatPinblock("6217588100000023418".getBytes(), "123456".getBytes());
//			System.out.println("PinBlock Value=" + (HexUtil.convertByteArrayToHexStr(pinBlock)).toUpperCase());
//			byte[]  passw = formatPinblockToPwd("6217588100000023418".getBytes(),pinBlock);
//			byte[] pass = doEncrypt(EncryptConst.ENCRY_3DES_ECB,key,pinBlock,null);
//			//byte[] tempMac = encryptPIN(EncryptConst.ENCRY_PIN_ANSI98, key,"6212720000001994".getBytes(), "123456".getBytes());
//			System.out.println("Pin Value=" + (HexUtil.convertByteArrayToHexStr(passw)).toUpperCase());
			
			byte[]  pinBlock = formatSM4Pinblock("6217588100000023418".getBytes(), "123456".getBytes());
			System.out.println(HexUtil.convertByteArrayToHexStr(pinBlock));
			pinBlock = PinblockSM4Pin("6217588100000023418".getBytes(),pinBlock);
			System.out.println(HexUtil.convertByteArrayToHexStr(pinBlock).toUpperCase().substring(2, 8));
			
			pinBlock = formatPinblock("6217588100000023418".getBytes(), "123456".getBytes());
			System.out.println(HexUtil.convertByteArrayToHexStr(pinBlock));
			pinBlock = EncryptUtil.formatPinblockToPwd(
					"6217588100000023418".getBytes(), pinBlock);
			System.out.println(HexUtil.convertByteArrayToHexStr(pinBlock));
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
