package com.fast.common.core.crypto;
import cn.hutool.core.codec.Base64;
import org.apache.logging.log4j.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fast.common.core.utils.HexUtil;





/**
 * @name EnctryptTools 加密方法封装
 * @author ZhouHuan Email:loveingowp@163.com
 * @date 2015-8-12
 */
public class EnctryptTools {
	private static final Logger LOG = LoggerFactory.getLogger(EnctryptTools.class);
	/**
	 * @Description 中国银联直联POS终端规范  POS终端MAC的算法 (适用于建行\中国银联)
	 * @Author ZhouHuan Email:loveingowp@163.com
	 * @Date 2015-8-12
	 * @version 1.0.0
	 * @param macStr 要加密的数据
	 * @param macKye MacKey 解密之后的MacKey明文
	 * @return 加密后数据
	 * @throws Exception 
	 */
	public static String ccbXorMac(byte[] macStr, byte[] macKye) throws Exception{
		int macStrLen = macStr.length;
		/**判断传入参数是否有问题*/
		if(macStrLen < 1){
			throw new Exception("传入需要计算Mac数据有误!");
		}
		
		/** 判断传入MacKey是否有问题***/
		if((macKye.length % 8 ) != 0){ 
			throw new Exception("传入MacKey数据有误!");
		}
		
		/**补足8 的倍数*/
		int dataLen = macStrLen + (8 - macStrLen%8)%8; 
		
		byte[] strData = new byte[dataLen];
		
		System.arraycopy(macStr, 0, strData, 0, macStrLen);
		
		/** 不足 补0X00**/
		for(int i=macStrLen; i<dataLen; i++){
			strData[i] = 0x00;
		}
		byte[] res = new byte[8];
		
		/**按每8个字节做异或 **/
		int arrayLen = dataLen / 8;
		for(int i=0; i<arrayLen; i++){
			for(int j=0; j<8; j++){
				res[j] ^=strData[i*8+j];
			}
		}
		
		/**将异或运算后的最后8个字节（RESULT BLOCK）转换成16 个HEXDECIMAL*/
		String block =  HexUtil.bytesToHexStr(res, 0, 0).toUpperCase();
		System.out.println("block:" + block);
		if(block.length() != 16){
			throw new Exception("8个字节(RESULT BLOCK)转换成16 个HEXDECIMAL 出错!");
		}
		
		/**取前8 个字节用MAK加密*/
		byte[] tempBuf = new byte[8];
		System.arraycopy(block.getBytes(),0,tempBuf,0,8);
		byte mode = 0;
		if(macKye.length == 16)
			 mode = 2;
		res = EncryptUtil.doEncrypt(mode, macKye, tempBuf, null);
		
		/**将加密后的结果与后8 个字节异或*/
		System.arraycopy(block.getBytes(), 8, tempBuf, 0, 8);
		for(int i=0; i<8; i++){
			tempBuf[i] ^=res[i];
		}
		
		/**用异或的结果TEMP BLOCK 再进行一次单倍长密钥算法运算*/
		res = EncryptUtil.doEncrypt(mode, macKye, tempBuf, null);
		
		
		/**将运算后的结果（ENC BLOCK2）转换成16 个HEXDECIMAL 取前8个字节作为MAC值*/
		return HexUtil.bytesToHexStr(res, 0, 8).toUpperCase();
	}
	
	
	
	 /// <summary>
    /// 前置MAC计算
    /// (1) ANSI X9.19MAC算法只使用双倍长密钥，也就是16字节密钥；
    /// (2) MAC数据按8字节分组，表示为D0～Dn，如果Dn不足8字节时，尾部以字节00补齐；
    /// (3) 用MAC密钥左半部加密D0，加密结果与D1异或作为下一次的输入。
    /// (4) 将上一步的加密结果与下一分组异或，然后用MAC密钥左半部加密。
    /// (5) 直至所有分组结束。
    /// (6) 用MAC密钥右半部解密(5)的结果。
    /// (7) 用MAC密钥左半部加密(6)的结果。
    /// (8) 取(7)的结果的左半部作为MAC。
    /// </summary>
    /// <param name="key">密钥</param>
    /// <param name="data">数据</param>
    /// <returns>16进制MAC值</returns>
    public static String StpMac(byte[] data, byte[] keyValue)  throws Exception{
        //把报文以8字节分组，最后一组不足8字节补0,
        int datalen = data.length;
        datalen += (8 - datalen % 8) % 8; //不足8位不足8位
        byte[] secdata = new byte[datalen];
        System.arraycopy(data, 0, secdata, 0, data.length);
        for (int i = data.length; i < datalen; i++)
        {
            secdata[i] = 0x00;
        }
        byte[] res = new byte[8];
        byte[] temp = new byte[8];

        byte[] key1 = new byte[8];
        byte[] key2 = new byte[8];

        System.arraycopy(keyValue, 0, key1, 0, key1.length); //左部分
        System.arraycopy(keyValue, 8, key2, 0, key1.length); //右部分

        /**按每8个字节做异或 **/
        int arrayLen = datalen / 8;
        for (int i = 0; i < arrayLen; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                temp[j] = secdata[i * 8 + j];
                temp[j] ^= res[j];
            }
            res = EncryptUtil.doEncrypt(EncryptConst.ENCRY_DES_ECB, key1, temp, null);
        }
        res =  EncryptUtil.doDecrypt(EncryptConst.DECRY_DES_ECB, key2, res, null);
        res = EncryptUtil.doEncrypt(EncryptConst.ENCRY_DES_ECB, key1, res, null);
        return HexUtil.bytesToHexStr(res, 0, 8).toUpperCase();
    }

	
	/**
	 * 金电公司 计算MAC方法
	 * @param macStr MAC串
	 * @param macKye MacKey
	 * @return
	 * @throws Exception
	 * @author ZhoHuan
	 * @date 2016-8-30
	 */
	public static String eviXorMac(byte[] macStr, byte[] macKye) throws Exception{
		int macStrLen = macStr.length;
		/**判断传入参数是否有问题*/
		if(macStrLen < 1){
			throw new Exception("传入需要计算Mac数据有误!");
		}
		
		/** 判断传入MacKey是否有问题***/
		if((macKye.length % 8 ) != 0){ 
			throw new Exception("传入MacKey数据有误!");
		}
		
		/**补足8 的倍数*/
		int dataLen = macStrLen + (8 - macStrLen%8)%8; 
		
		byte[] strData = new byte[dataLen];
		
		System.arraycopy(macStr, 0, strData, 0, macStrLen);
		
		/** 不足 补0X00**/
		for(int i=macStrLen; i<dataLen; i++){
			strData[i] = 0x00;
		}
		byte[] res =new byte[8];
		
		/**按每8个字节做异或 **/
		int arrayLen = dataLen / 8;
		for(int i=0; i<arrayLen; i++){
			for(int j=0; j<8; j++){
				res[j] ^=strData[i*8+j];
			}
		}
		/**将异或运算后的最后8个字节（RESULT BLOCK）转换成16 个HEXDECIMAL*/
		String block =  HexUtil.bytesToHexStr(res, 0, 0).toUpperCase();
		LOG.info("mab_data:" + block);
		byte mode = 0;
		if(macKye.length == 16) {
			mode = 2;
		}
		byte[] mes = EncryptUtil.doEncrypt(mode, macKye, res, null);
		/**将运算后的结果（ENC BLOCK2）转换成16 个HEXDECIMAL 取前16个字节作为MAC值*/
		return HexUtil.bytesToHexStr(mes, 0, 8).toUpperCase();
	}
	
	/**
	 * 
	 *  兴业前置SM4MAC计算方法
	 * @author ZhouHuan Email:loveingowp@163.com
	 * @date Aug 21, 2018
	 * @version 1.0.1
	 * @param keyValue
	 * @param data
	 * @return
	 *
	 */
    public static byte[] posSM4Mac(byte[] keyValue, byte[] data)
    {
        //把报文以8字节分组，最后一组不足8字节补0,
        //byte[] data = Encoding.Default.GetBytes(mac);
        int datalen = data.length;
        datalen += (16 - datalen % 16) % 16; //不足16位不足16位
        byte[] secdata = new byte[datalen];
        System.arraycopy(data, 0, secdata, 0, data.length);
        for (int i = data.length; i < datalen; i++)
        {
            secdata[i] = 0x00;
        }
        byte[] res = new byte[16];
        byte[] temp = new byte[16];

        //yte[] key1 = new byte[8];
        //byte[] key2 = new byte[8];

        //Array.Copy(keyValue, 0, key1, 0, key1.Length); //左部分
        //Array.Copy(keyValue, 8, key2, 0, key1.Length); //右部分

        /**按每8个字节做异或 **/
        int arrayLen = datalen / 16;
        for (int i = 0; i < arrayLen; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                // DESEncrypt0(secdata[i * 8 + j], key1);
                temp[j] = secdata[i * 16 + j];
                temp[j] ^= res[j];
            }
            //res = Encrypt(temp, key1);
            // res = SM4.EncriptB(temp, keyValue);
            SM4.GMSM4(temp, temp.length, keyValue, res, SM4.ENCRYPT);
            //res = convertHexStrToByteArray(Encrypt3Des(convertByteArrayToHexStr(res), key));
        }
        //res = Decrypt(res, key2);
        //res = Encrypt(res, key1);
        return HexUtil.bytesToHexStr(res, 0, 4).toUpperCase().getBytes();
    }


	public static String SM4Mac(byte[] keyValue, byte[] data)
	{
		//把报文以8字节分组，最后一组不足8字节补0,
		//byte[] data = Encoding.Default.GetBytes(mac);
		int datalen = data.length;
		datalen += (16 - datalen % 16) % 16; //不足16位不足16位
		byte[] secdata = new byte[datalen];
		System.arraycopy(data, 0, secdata, 0, data.length);
		for (int i = data.length; i < datalen; i++)
		{
			secdata[i] = 0x00;
		}
		byte[] res = new byte[16];
		byte[] temp = new byte[16];

		//yte[] key1 = new byte[8];
		//byte[] key2 = new byte[8];

		//Array.Copy(keyValue, 0, key1, 0, key1.Length); //左部分
		//Array.Copy(keyValue, 8, key2, 0, key1.Length); //右部分

		/**按每8个字节做异或 **/
		int arrayLen = datalen / 16;
		for (int i = 0; i < arrayLen; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				// DESEncrypt0(secdata[i * 8 + j], key1);
				temp[j] = secdata[i * 16 + j];
				temp[j] ^= res[j];
			}
			//res = Encrypt(temp, key1);
			// res = SM4.EncriptB(temp, keyValue);
			SM4.GMSM4(temp, temp.length, keyValue, res, SM4.ENCRYPT);
			//res = convertHexStrToByteArray(Encrypt3Des(convertByteArrayToHexStr(res), key));
		}
		//res = Decrypt(res, key2);
		//res = Encrypt(res, key1);
		return HexUtil.convertByteArrayToHexStr(res);
	}
    
    /**
     * 兴业VTM 网银加密方法
     * @Description:3DES 3倍KEY 加密
     * @author ZhouHuan 18774995071@163.com
     * @time 2019-07-30 22:14
     * @param key 24字节key
     * @param pan 账号
     * @param pin 密码
     * @param mode LEVEL >= 2 -- 打日志 
     * @return
     * @throws Exception
     *
     */
    public static String DES3KKYEncrypt(byte[] key,byte[] pan,byte[] pin, int mode) throws Exception {
    	
    	byte[] Pinblock = EncryptUtil.formatPinblock(pan,pin);
    	
		if(mode >= 2) {
			LOG.info("Pinblock:" + HexUtil.convertByteArrayToHexStr(Pinblock));
		}
		
		byte[] LK = new byte[8];  //可以分为LK（密钥的左边8字节）,
		System.arraycopy(key, 0, LK, 0, 8);
		byte[] CK = new byte[8];  //CK（密钥的中间8字节）
		System.arraycopy(key, 8, CK, 0, 8);
		byte[] RK = new byte[8];//RK（密钥的左边8字节）
		System.arraycopy(key, 16, RK, 0, 8);
		/**
		 	只是第一次计算，使用密钥LK；第二次计算，使用密钥CK；第三次计算，使用密钥LK。基本过程如下：
			DES( DATA, LK, TMP1 );
			UDES( TMP1, CK, TMP2 );
			DES( TMP2, RK, DEST );
		 */
		byte[]  TMP1 = EncryptUtil.doEncrypt(EncryptConst.ENCRY_DES_ECB, LK, Pinblock, null); 
		
		byte[]  TMP2 = EncryptUtil.doDecrypt(EncryptConst.ENCRY_DES_ECB, CK, TMP1, null); 
		
		byte[]  DEST = EncryptUtil.doEncrypt(EncryptConst.ENCRY_DES_ECB, RK, TMP2, null); 
		
		if(mode >= 2) {
			LOG.info("加密后密文:" + HexUtil.convertByteArrayToHexStr(DEST).toUpperCase());
		}
		return HexUtil.convertByteArrayToHexStr(DEST).toUpperCase();
    }
    
    
    /**
	 * 
	 *  SM4MAC计算方法
	 * @author ZhouHuan Email:loveingowp@163.com
	 * @date Aug 21, 2018
	 * @version 1.0.1
	 * @param keyValue
	 * @param data
	 * @return
	 *
	 */
    public static String StpSM4Mac(byte[] data,byte[] keyValue)
    {
        //把报文以8字节分组，最后一组不足8字节补0,
        //byte[] data = Encoding.Default.GetBytes(mac);
        int datalen = data.length;
        datalen += (16 - datalen % 16) % 16; //不足16位不足16位
        byte[] secdata = new byte[datalen];
        System.arraycopy(data, 0, secdata, 0, data.length);
        for (int i = data.length; i < datalen; i++)
        {
            secdata[i] = 0x00;
        }
        byte[] res = new byte[16];
        byte[] temp = new byte[16];

        //yte[] key1 = new byte[8];
        //byte[] key2 = new byte[8];

        //Array.Copy(keyValue, 0, key1, 0, key1.Length); //左部分
        //Array.Copy(keyValue, 8, key2, 0, key1.Length); //右部分

        /**按每8个字节做异或 **/
        int arrayLen = datalen / 16;
        for (int i = 0; i < arrayLen; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                // DESEncrypt0(secdata[i * 8 + j], key1);
                temp[j] = secdata[i * 16 + j];
                temp[j] ^= res[j];
            }
            //res = Encrypt(temp, key1);
            // res = SM4.EncriptB(temp, keyValue);
            SM4.GMSM4(temp, temp.length, keyValue, res, SM4.ENCRYPT);
            //res = convertHexStrToByteArray(Encrypt3Des(convertByteArrayToHexStr(res), key));
        }
        //res = Decrypt(res, key2);
        //res = Encrypt(res, key1);
        return HexUtil.bytesToHexStr(res, 0, 8).toUpperCase();
    }
	
	/**
	 * 
	 *     SM4密码加密
	 *  
	 * @author ZhouHuan Email:loveingowp@163.com
	 * @date Aug 21, 2018
	 * @version 1.0.1
	 * @param key 密钥
	 * @param cardNo 卡号
	 * @param PIN 密码明文
	 * @return 密码密文
	 *
	 */
	public static byte[] encryptSM4Pass(byte[] key, String cardNo, String PIN){
		byte[] pinBlok = EncryptUtil.formatSM4Pinblock(cardNo.getBytes(), PIN.getBytes());
		//LOG.info("SM4Pinblock:" + HexUtil.convertByteArrayToHexStr(pinBlok));
		byte[] out = new byte[16];
		SM4.GMSM4(pinBlok, pinBlok.length, key, out, SM4.ENCRYPT);
		return out; 
	}
	
	/**
	 * 
	 *  SM4密码解密
	 * @author ZhouHuan 18774995071@163.com
	 * @time 2019-05-24 11:01
	 * @param key 密钥
	 * @param cardNo 卡号
	 * @param PIN 密码密文
	 * @return
	 *
	 */
	public static String decryptSM4Pass(byte[] key, String cardNo, String PIN){
		byte[] hexPin = HexUtil.convertHexStrToByteArray(PIN);
		byte[] out = new byte[16];
		SM4.GMSM4(hexPin, hexPin.length, key, out, SM4.DECRYPT);
		return HexUtil.convertByteArrayToHexStr(EncryptUtil.PinblockSM4Pin(cardNo.getBytes(),out)).toUpperCase().substring(2, 8); 
	}
	
	/**
	 * 
	 * @Description:网银密码加密
	 * @author ZhouHuan 18774995071@163.com
	 * @time 2019-07-12 10:31
	 * @param key 网银密钥
	 * @param cardNo 卡号
	 * @param PIN 密码明文
	 * @return 密码密文
	 * @throws Exception 
	 *
	 */
	public static String WekDecryptPass(byte[] key, String cardNo, String PIN) throws Exception {
		//1. 计算PINBLOCK
		byte[] pinBlok = EncryptUtil.formatPinblock(cardNo.getBytes(),PIN.getBytes());
		LOG.info("Pinblock:" + HexUtil.convertByteArrayToHexStr(pinBlok));
		//2.加密
		byte[] pinm = EncryptUtil.doEncrypt(EncryptConst.DECRY_3DES_CBC, key, pinBlok, null);
		
		//3.转换16进制
		return HexUtil.convertByteArrayToHexStr(pinm);
	}


	/**
	 * 密文解密成明文
	 * @param str b64 字符
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String DesDecode(String str,String key) throws Exception{
		//
		int datalen = key.getBytes().length;
		datalen += (16 - datalen % 16) % 16; //不足16位不足16位
		byte[] secdata = new byte[datalen];
		System.arraycopy(key.getBytes(), 0, secdata, 0, key.getBytes().length);
		for (int i = key.getBytes().length; i < datalen; i++)
		{
			secdata[i] = 0x00;
		}
		byte[] res = new byte[16];
		byte[] temp = new byte[16];

		/**按每8个字节做异或 **/
		int arrayLen = datalen / 16;
		for (int i = 0; i < arrayLen; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				temp[j] = secdata[i * 16 + j];
				temp[j] ^= res[j];
			}
		}
		byte[] rd = Base64.decode(str);
		byte[] send = new byte[rd.length + (8 -rd.length % 8)%8];
		System.arraycopy(rd, 0, send, 0, rd.length);
		byte[] value = EncryptUtil.doDecrypt((byte)2,temp,send, null);
		value = HexUtil.trimLast0x00(value);
		return new String(value);
	}


	/**
	 * 密文解密成明文
	 * @param str b64 字符
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String SM4Decode(String str,String key) throws Exception{
		//
		int datalen = key.getBytes().length;
		datalen += (16 - datalen % 16) % 16; //不足16位不足16位
		byte[] secdata = new byte[datalen];
		System.arraycopy(key.getBytes(), 0, secdata, 0, key.getBytes().length);
		for (int i = key.getBytes().length; i < datalen; i++)
		{
			secdata[i] = 0x00;
		}
		byte[] res = new byte[16];
		byte[] temp = new byte[16];

		/**按每8个字节做异或 **/
		int arrayLen = datalen / 16;
		for (int i = 0; i < arrayLen; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				temp[j] = secdata[i * 16 + j];
				temp[j] ^= res[j];
			}
		}
		byte[] rd = Base64.decode(str);
		byte[] send = new byte[rd.length + (16 -rd.length % 16)%16];
		System.arraycopy(rd, 0, send, 0, rd.length);
		byte[] value = new byte[send.length];
		SM4.GMSM4(send, send.length, temp, value, SM4.DECRYPT);
		value = HexUtil.trimLast0x00(value);
		return new String(value);
	}

	/**
	 *明文变成加密密文
	 * @param str 明文
	 * @param key 密钥
	 * @return
	 * @throws Exception
	 */
	public static String DesEncode(String str, String key) throws Exception{
		int datalen = key.getBytes().length;
		//不足16位不足16位
		datalen += (16 - datalen % 16) % 16;
		byte[] secdata = new byte[datalen];
		System.arraycopy(key.getBytes(), 0, secdata, 0, key.getBytes().length);
		for (int i = key.getBytes().length; i < datalen; i++)
		{
			secdata[i] = 0x00;
		}
		byte[] res = new byte[16];
		byte[] temp = new byte[16];

		/**按每8个字节做异或 **/
		int arrayLen = datalen / 16;
		for (int i = 0; i < arrayLen; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				temp[j] = secdata[i * 16 + j];
				temp[j] ^= res[j];
			}
		}

		byte[] txtb = str.getBytes();
		byte[] send = new byte[txtb.length + (8 - txtb.length % 8) % 8];
		System.arraycopy(txtb, 0, send, 0, txtb.length);
		byte[] value = EncryptUtil.doEncrypt((byte)2,temp,send, null);
		return Base64.encode(value);
	}

	public static String SM4Encode(String str, String key) throws Exception{
		int datalen = key.getBytes().length;
		//不足16位不足16位
		datalen += (16 - datalen % 16) % 16;
		byte[] secdata = new byte[datalen];
		System.arraycopy(key.getBytes(), 0, secdata, 0, key.getBytes().length);
		for (int i = key.getBytes().length; i < datalen; i++)
		{
			secdata[i] = 0x00;
		}
		byte[] res = new byte[16];
		byte[] temp = new byte[16];

		/**按每8个字节做异或 **/
		int arrayLen = datalen / 16;
		for (int i = 0; i < arrayLen; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				temp[j] = secdata[i * 16 + j];
				temp[j] ^= res[j];
			}
		}

		byte[] txtb = str.getBytes();
		byte[] send = new byte[txtb.length + (16 - txtb.length % 16) % 16];
		System.arraycopy(txtb, 0, send, 0, txtb.length);

		byte[] value = new byte[send.length];
		SM4.GMSM4(send, send.length, temp, value, SM4.ENCRYPT);
		return Base64.encode(value);
	}

}
