package com.fast.common.core.crypto;
import java.io.IOException;
import java.math.BigInteger;

import com.fast.common.core.utils.HexUtil;
import com.fast.common.core.utils.ToolUtil;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SM2Utils
{
    private static final Logger logger = LoggerFactory.getLogger(SM2Utils.class);
    public static final int IS_C1C3C2 = 1;
    public static final int IS_C1C2C3 = 2;
    public static final int PUBLIC_KEY_SIZE = 64;
    public static final int PRIVATE_KEY_SIZE = 32;
    public static final int SIGN_S_R_SIZE = 32;
    public static final int C1_SIZE = 65;
    public static final int C3_SIZE = 32;

    public static byte[] generateKeyPair()
    {
        SM2 sm2 = SM2.Instance();
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters)key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters)key.getPublic();
        BigInteger privateKey = ecpriv.getD();
        ECPoint publicKey = ecpub.getQ();
        byte[] publicKeyEncoded = publicKey.getEncoded();

        if (publicKeyEncoded.length == 65) {
            publicKeyEncoded = HexUtil.subByte(publicKeyEncoded, 1, publicKeyEncoded.length - 1);
        }
        byte[] privateKeyEncoded = privateKey.toByteArray();
        if (privateKeyEncoded.length == 33) {
            privateKeyEncoded = HexUtil.subByte(privateKeyEncoded, 1, privateKeyEncoded.length - 1);
        }

        return HexUtil.pinJie2(privateKeyEncoded, publicKeyEncoded);
    }

    public static byte[] encrypt(byte[] publicKey, byte[] data, int type) //112 - 32 = 80  -- 64 = 16
            throws IOException
    {
        if ((ToolUtil.isEmpty(publicKey)) || (ToolUtil.isEmpty(data))) {
            return null;
        }

        if (publicKey.length == 64) {
            publicKey = HexUtil.pinJie2(new byte[] { 4 }, publicKey);
        }
        byte[] source = new byte[data.length];
        System.arraycopy(data, 0, source, 0, data.length);

        Cipher cipher = new Cipher();
        SM2 sm2 = SM2.Instance();
        ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);

        ECPoint c1 = cipher.Init_enc(sm2, userKey);
        cipher.Encrypt(source);
        byte[] c3 = new byte[32];
        cipher.Dofinal(c3);
        if (1 == type)
        {
            return HexUtil.pinJie3(HexUtil.subByte(c1.getEncoded(), 1, 64), c3, source);
        }if (2 == type)
    {
        return HexUtil.pinJie3(c1.getEncoded(), source, c3);
    }
        return null;
    }

    public static byte[] decrypt(byte[] privateKey, byte[] encryptedData, int type)
            throws IOException
    {
        if ((ToolUtil.isEmpty(privateKey)) || (ToolUtil.isEmpty(encryptedData))) {
            return null;
        }

        encryptedData = HexUtil.pinJie2(new byte[] { 4 }, encryptedData);
        byte[] c1Bytes = HexUtil.subByte(encryptedData, 0, 65);
        int c2Len = encryptedData.length - 65 - 32;
        if (1 == type)
        {
            int c1c3Length = 97;
            byte[] c3 = HexUtil.subByte(encryptedData, 65, 32);
            byte[] c2 = HexUtil.subByte(encryptedData, c1c3Length, c2Len);
            SM2 sm2 = SM2.Instance();
            BigInteger userD = new BigInteger(1, privateKey);
            ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
            Cipher cipher = new Cipher();
            cipher.Init_dec(userD, c1);
            cipher.Decrypt(c2);
            cipher.Dofinal(c3);
            return c2;
        }if (2 == type)
        {
            int c1c2Length = 65 + c2Len;
            byte[] c2 = HexUtil.subByte(encryptedData, 65, c2Len);
            byte[] c3 = HexUtil.subByte(encryptedData, c1c2Length, 32);
            SM2 sm2 = SM2.Instance();
            BigInteger userD = new BigInteger(1, privateKey);

            ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
            Cipher cipher = new Cipher();
            cipher.Init_dec(userD, c1);
            cipher.Decrypt(c2);
            cipher.Dofinal(c3);

            return c2;
        }
            return null;
    }


    //数据解密
    public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws IOException
    {
        if (privateKey == null || privateKey.length == 0)
        {
            return null;
        }

        if (encryptedData == null || encryptedData.length == 0)
        {
            return null;
        }
        //encryptedData = SMUtil.pinJie2(new byte[] { 4 }, encryptedData);

        //112
        //加密字节数组转换为十六进制的字符串 长度变为encryptedData.length * 2
        String data = "04"+HexUtil.encodeHexStr(encryptedData);
        /***分解加密字串
         * （C1 = C1标志位2位 + C1实体部分128位 = 130）
         * （C3 = C3实体部分64位  = 64）
         * （C2 = encryptedData.length * 2 - C1长度  - C2长度）
         */
//        byte[] c1Bytes = Util.hexToByte(data.substring(0,130));
//        int c2Len = encryptedData.length - 97;
//        byte[] c2 = Util.hexToByte(data.substring(130,130 + 2 * c2Len));
//        byte[] c3 = Util.hexToByte(data.substring(130 + 2 * c2Len,194 + 2 * c2Len));

        byte[] c1Bytes = HexUtil.decodeHex(data.substring(0,130));
        System.out.println("c1:"+data.substring(0,130));
        int c2Len = encryptedData.length + 1 - 97;
        byte[] c2 = HexUtil.decodeHex(data.substring(194,194 + 2 * c2Len));
        System.out.println("c2:"+data.substring(194,194 + 2 * c2Len));
        byte[] c3 = HexUtil.decodeHex(data.substring(130,194));
        SM2 sm2 = SM2.Instance();
        BigInteger userD = new BigInteger(1, privateKey);
        //通过C1实体字节来生成ECPoint
        ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
        Cipher cipher = new Cipher();
        cipher.Init_dec(userD, c1);
        cipher.Decrypt(c2);
        cipher.Dofinal(c3);

        //返回解密结果
        return c2;
    }

    public static byte[] sign(byte[] privateKey, byte[] sourceData, byte[] userId, boolean isDigest)
            throws Exception
    {
        if ((ToolUtil.isEmpty(privateKey)) || (ToolUtil.isEmpty(sourceData))) {
            return null;
        }
        SM2 sm2 = SM2.Instance();
        String privateKeyStr = HexUtil.encodeHexStr(privateKey);
        BigInteger userD = new BigInteger(privateKeyStr, 16);
        ECPoint userKey = sm2.ecc_point_g.multiply(userD);

        SM2Result sm2Result = new SM2Result();
        sm2.sm2Sign(sourceData, userD, userKey, sm2Result);

        String r = sm2Result.r.toString(16);
        String s = sm2Result.s.toString(16);
        return HexUtil.convertHexStrToByteArray(new StringBuilder().append(padding(r)).append(padding(s)).toString());
    }

    public static String padding(String result)
    {
        if ((result != null) && (!result.equals("")) && (result.length() < 64)) {
            int length = 64 - result.length();
            StringBuilder resultString = new StringBuilder();
            for (int i = 0; i < length; i++) {
                resultString.append("0");
            }
            resultString.append(result);
            return resultString.toString();
        }
        return result;
    }

    public static boolean verifySign(byte[] publicKey, byte[] sourceData, byte[] signData, byte[] userId, boolean isDigest)
            throws IOException
    {
        if ((ToolUtil.isEmpty(publicKey)) || (ToolUtil.isEmpty(sourceData))) {
            return false;
        }

        if (publicKey.length == 64) {
            publicKey = HexUtil.pinJie2(new byte[] { 4 }, publicKey);
        }

        SM2 sm2 = SM2.Instance();
        ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);
        if (!isDigest)
        {
            SM3Digest sm3 = new SM3Digest();
            if (!ToolUtil.isEmpty(userId)) {
                byte[] z = sm2.sm2GetZ(userId, userKey);
                sm3.update(z, 0, z.length);
            }
            sm3.update(sourceData, 0, sourceData.length);
            byte[] md = new byte[32];
            sm3.doFinal(md, 0);
            sourceData = md;
        }
        String rStr = HexUtil.encodeHexStr(HexUtil.subByte(signData, 0, 32));
        String sStr = HexUtil.encodeHexStr(HexUtil.subByte(signData, 32, 32));
        BigInteger r = new BigInteger(rStr, 16);
        BigInteger s = new BigInteger(sStr, 16);
        SM2Result sm2Result = new SM2Result();
        sm2Result.r = r;
        sm2Result.s = s;
        sm2.sm2Verify(sourceData, userKey, sm2Result.r, sm2Result.s, sm2Result);
        return sm2Result.r.equals(sm2Result.R);
    }
}
