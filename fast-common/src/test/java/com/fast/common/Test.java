package com.fast.common;

import com.fast.common.core.crypto.SoftEncryption;
import com.fast.common.core.utils.HexUtil;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SmUtil;

public class Test {
	
	public static void main(String[] args) {
		String name = "admin";
        byte[] txt = HexUtil.Hex16ToBytes(name.getBytes());
        System.out.println(new String(HexUtil.Hex16ToBytes(name.getBytes())));

        String hex =  SoftEncryption.genRandom(16).getStr("hex");
          System.out.println("fd8ba11940284fb989c4f275c5fdfc80");
     byte[] a =  "11HDESaAhiHHugDz".getBytes();
          System.out.println("11HDESaAhiHHugDz:"+HexUtil.encodeHexStr("11HDESaAhiHHugDz".getBytes()));
        System.out.println(new String(HexUtil.decodeHex("fd8ba11940284fb989c4f275c5fdfc80")));
      String  rs = SoftEncryption.encryptBySM4(txt,a).getStr("b64"); //4UtjdUpbq1958YySIhkkWg==
      byte[] r = SoftEncryption.encryptBySM4(txt,a).get("bytes",byte[].class);
      System.out.println(rs);
     
//      String s = Base64.encode(SmUtil.sm4(a).encrypt(txt));
//      System.out.println(s);
	}
}
