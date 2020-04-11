package com.j2eefast.common.core.crypto;

import com.j2eefast.common.core.exception.ServiceException;
import com.j2eefast.common.core.utils.HexUtil;
import com.j2eefast.common.core.utils.ToolUtil;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class SoftEncryption {
    private static final Logger LOG = LoggerFactory.getLogger(SoftEncryption.class);
    public boolean connectDevice()
    {
        LOG.info("===========当前是软加密==============");
        return true;
    }

    public void closeConnect()
    {
    }

    /**
     * 生成服务端密钥 公钥与私钥
     * @return
     */
    public  static JSONObject genSM2Keys()
    {
        try
        {
            JSONObject returnData = new JSONObject();

            byte[] keys = SM2Utils.generateKeyPair();

            byte[] priKey = HexUtil.subByte(keys, 0, 32);
            byte[] pubKey = HexUtil.subByte(keys, 32, 64);
            returnData.put("state", Boolean.valueOf(true)).put("pubKey", HexUtil.encodeHexStr(pubKey))
                    .put("priKey",
                            HexUtil.encodeHexStr(priKey)).put("pubKeyByte",pubKey).put("priKeyByte",pubKey);
            return returnData;
        } catch (Exception e) {
            LOG.error("===========使用SM2算法生成秘钥对失败===========");
            LOG.error(ToolUtil.getMessage(e));
        }throw new ServiceException("E0XA00011");
    }

    public static JSONObject encryptBySM2(byte[] data, byte[] pubKey)
    {
        JSONObject returnData = new JSONObject();
        try {
            byte[] cipher = SM2Utils.encrypt(pubKey, data, 1);
            returnData.put("state", Boolean.valueOf(true))
                    .put("bytes", cipher)
                    .put("hex",
                            HexUtil.encodeHexStr(cipher)).put("b64",Base64.encode(cipher));;
        }
        catch (Exception e) {
            LOG.error("===========使用SM2算法加密失败==========");
            LOG.error(ToolUtil.getMessage(e));
            throw new ServiceException("E0XA00011");
        }
        return returnData;
    }

    public static JSONObject decryptBySM2(byte[] data, byte[] priKey)
    {
        JSONObject returnData = new JSONObject();
        try {
            byte[] source = SM2Utils.decrypt(priKey, data,1);
            returnData.put("state", Boolean.valueOf(true))
                    .put("hex",
                            HexUtil.encodeHexStr(source))
                    .put("bytes", source);
        }
        catch (Exception e) {
            LOG.error("===========使用SM2算法解密失败==========");
            LOG.error(ToolUtil.getMessage(e));
            throw new ServiceException("E0XA00011");
        }
        return returnData;
    }

    public static JSONObject signDigest(byte[] data, byte[] priKey)
    {
        JSONObject returnData = new JSONObject();
        try {
            byte[] sign = SM2Utils.sign(priKey, data, null, true);
            returnData.put("state", Boolean.valueOf(true))
                    .put("bytes", sign)
                    .put("hex",
                            HexUtil.encodeHexStr(sign)).put("b64",Base64.encode(sign));
        }
        catch (Exception e) {
            LOG.error("===========使用SM2算法对摘要签名失败==========");
            LOG.error(ToolUtil.getMessage(e));
            throw new ServiceException("E0XA00011");
        }
        return returnData;
    }

    public static boolean verifyDigest(byte[] data, byte[] sign, byte[] pubKey)
    {
        try
        {
            return SM2Utils.verifySign(pubKey, data, sign, null, true);
        }
        catch (Exception e) {
            LOG.error("===========使用SM2算法对摘要签名验证失败==========");
            LOG.error(ToolUtil.getMessage(e));
        }throw new ServiceException("E0XA00011");
    }

    public static JSONObject genSM3Keys(byte[] data)
    {
        JSONObject returnData = new JSONObject();
        try {
            byte[] result = SM3Digest.update(data);
            return returnData.put("state", Boolean.valueOf(true))
                    .put("bytes", result)
                    .put("hex",
                            HexUtil.encodeHexStr(result)).put("b64", Base64.encode(result));
        }
        catch (Exception e) {
            LOG.error("===========SM3算法摘要失败==========");
            LOG.error(ToolUtil.getMessage(e));
        }throw new ServiceException("E0XA00011");
    }

    public static JSONObject genRandom(int size)
    {
        try
        {
            JSONObject returnData = new JSONObject();

            if (16 == size) {
                String keys = IdUtil.fastSimpleUUID();
                //System.out.println(HexUtil.convertByteArrayToHexStr(HexUtil.decodeHex(keys)));
                return returnData.put("state", Boolean.valueOf(true))
                        .put("hex",
                                keys)
                        .put("bytes",
                                HexUtil.decodeHex(keys));
            }

            String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            int str_len = str.length();
            if (size <= 0) {
                size = 1;
            }
            Random random = new Random();
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < size; i++) {
                res.append(str.charAt(random.nextInt(str_len)));
            }
            String keys = res.toString();
            return returnData.put("state", Boolean.valueOf(true))
                    .put("hex",
                            HexUtil.encodeHexStr(keys
                                    .getBytes()))
                    .put("bytes", keys
                            .getBytes());
        }
        catch (Exception e)
        {
            LOG.error("===========生成随机数失败==========");
            LOG.error(ToolUtil.getMessage(e));
        }throw new ServiceException("E0XA00011");
    }

    public static JSONObject encryptBySM4(byte[] data, byte[] key)
    {
        try
        {
            JSONObject returnData = new JSONObject();
            byte[] result = SM4.encryptData_ECB(data,key);
            returnData.put("state", Boolean.valueOf(true))
                    .put("bytes", result)
                    .put("hex",
                            HexUtil.encodeHexStr(result)).put("b64",Base64.encode(result));

            return returnData;
        } catch (Exception e) {
            LOG.error("===========使用SM4算法加密失败==========");
            LOG.error(ToolUtil.getMessage(e));
        }throw new ServiceException("E0XA00011");
    }

    public static JSONObject decryptBySM4(byte[] ciphertext, byte[] key)
    {
        try
        {
            JSONObject returnData = new JSONObject();
            byte[] result = SM4.decryptData_ECB(ciphertext,key);
            returnData.put("state", Boolean.valueOf(true))
                    .put("bytes", result)
                    .put("hex",
                            HexUtil.encodeHexStr(result)).put("b64",Base64.encode(result));
            return returnData;
        } catch (Exception e) {
            LOG.error("===========使用SM4算法解密失败==========");
            LOG.error(ToolUtil.getMessage(e));
        }throw new ServiceException("E0XA00011");
    }


    /**
     * 生成安芯钥匙通讯数据加密
     * @param deviceId
     * @param devicePubKey 钥匙公钥
     * @param batchPriKey 服务器私钥
     * @param data
     * @return
     */
    public static JSONObject getCiphertext(String deviceId, String devicePubKey, String batchPriKey, byte[] data)
    {
        JSONObject returnData = null;
        try {
            returnData = new JSONObject();
            JSONObject result = genRandom(16);
            boolean state = result.getBool("state").booleanValue();
            if (state) {
                String key = result.getStr("hex");

                result = encryptBySM2(HexUtil.decodeHex(deviceId + key), HexUtil.decodeHex(devicePubKey));
                state = result.getBool("state").booleanValue();
                if (state) {
                    byte[] envelope = result.get("bytes",byte[].class);
                    String envelopeHex = result.getStr("hex");
                    result = genSM3Keys(data);
                    state = result.getBool("state").booleanValue();
                    if (state) {
                        String zy = result.getStr("hex");

                        result = signDigest(result.get("bytes",byte[].class), HexUtil.decodeHex(batchPriKey));
                        state = result.getBool("state").booleanValue();
                        if (state) {
                            byte[] signature = result.get("bytes",byte[].class);
                            String signatureHex = result.getStr("hex");

                            result = encryptBySM4(data, HexUtil.decodeHex(key));
                            state = result.getBool("state").booleanValue();
                            if (state) {
                                byte[] ciphertext = result.get("bytes",byte[].class);
                                String ciphertextStr = result.getStr("hex");
                                returnData.put("state", Boolean.valueOf(true)).put("envelope", envelope).put("envelopeHex",envelopeHex).put("signature", signature).
                                        put("signatureHex", signatureHex).put("ciphertext", ciphertext).put("ciphertextStr", ciphertextStr).put("zy", zy).
                                        put("hex", result.getStr("hex")).put("key", key);
                            } else {
                                returnData.put("state", Boolean.valueOf(false));
                            }
                        } else {
                            returnData.put("state", Boolean.valueOf(false));
                        }
                    } else {
                        returnData.put("state", Boolean.valueOf(false));
                    }
                } else {
                    returnData.put("state", Boolean.valueOf(false));
                }
            } else {
                returnData.put("state", Boolean.valueOf(false));
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return returnData;
    }
}
