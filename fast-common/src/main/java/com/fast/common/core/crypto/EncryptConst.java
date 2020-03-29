package com.fast.common.core.crypto;

import java.util.HashMap;

/**
 * ���ӿ�
 * @author chenhantao
 */
public interface EncryptConst
{
	public final static byte ENCRY_DES_ECB = 0x00;// 0-ΪDES ECB����

	public final static byte ENCRY_DES_CBC = 0x01;// 1-ΪDES CBC����

	public final static byte ENCRY_3DES_ECB = 0x02;// 2-Ϊ3DES ECB����

	public final static byte ENCRY_3DES_CBC = 0x03;// 3-Ϊ3DES CBC����

	public final static byte DECRY_DES_ECB = 0x00;// 0-ΪDES ECB����

	public final static byte DECRY_DES_CBC = 0x01;// 1-ΪDES CBC����

	public final static byte DECRY_3DES_ECB = 0x02;// 2-Ϊ3DES ECB����

	public final static byte DECRY_3DES_CBC = 0x03;// 3-Ϊ3DES CBC����

	public final static byte ENCRY_MAC_ANSI99 = 0x00;// 0-Ϊ���Mac ANSI9.9��Կ

	public final static byte ENCRY_MAC_ANSI919 = 0x01;// 1-Ϊ���Mac ANSI9.19��Կ

	/** �����Ǽ���PINBLOCK�㷨* */
	public final static byte ENCRY_PIN_ANSI98 = 0x00;// 0-Ϊ����pin ANSI X9.8�㷨 2-IBM3624

	public final static byte ENCRY_PIN_ISO9564_1 = 0x01;// 1-Ϊ����pin ISO 9564-1�㷨

	public final static byte ENCRY_MAC_KEY = 0x00;// ����Mac keyֵ

	public final static byte ENCRY_PIN_KEY = 0x01;// ����Pin keyֵ

	public final static byte ENCRY_WORK_KEY = 0x02;// ����work keyֵ

	public final static String INIT_WORK_KEY = "0000000000000000";// ��ʼwork keyֵ
	
	public final static String MASTER_WORKKEY = "43A789FC2D01B56F";// ��ʼmaster workkeyֵ

//	public final static String MASTER_KEY = "6666666666666666";// ��ʼmaster keyֵ
//	
//	public final static String MASTER_KEY_DES = "D720DABDB41AC1A5";// ��ʼmaster keyֵ,����
//
//	public final static String ROOT_KEY = "8888888888888888";// ��ʼroot  keyֵ,����
	
	public static HashMap TermWorkKeyMap = new HashMap();
}
