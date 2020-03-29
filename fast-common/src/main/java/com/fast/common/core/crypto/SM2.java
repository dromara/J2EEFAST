package com.fast.common.core.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;
import com.fast.common.core.utils.HexUtil;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;


public class SM2
{
    //正式参数
    public static String[] ecc_param = {
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF",
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC",
            "28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93",
            "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123",
            "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7",
            "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0"
    };
    public final BigInteger ecc_p;
    public final BigInteger ecc_a;
    public final BigInteger ecc_b;
    public final BigInteger ecc_n;
    public final BigInteger ecc_gx;
    public final BigInteger ecc_gy;
    public final ECCurve ecc_curve;
    public final ECPoint ecc_point_g;
    public final ECDomainParameters ecc_bc_spec;
    public final ECKeyPairGenerator ecc_key_pair_generator;
    public final ECFieldElement ecc_gx_fieldelement;
    public final ECFieldElement ecc_gy_fieldelement;

    public static SM2 Instance()
    {
        return new SM2();
    }

    public SM2()
    {
        this.ecc_p = new BigInteger(ecc_param[0], 16);
        this.ecc_a = new BigInteger(ecc_param[1], 16);
        this.ecc_b = new BigInteger(ecc_param[2], 16);
        this.ecc_n = new BigInteger(ecc_param[3], 16);
        this.ecc_gx = new BigInteger(ecc_param[4], 16);
        this.ecc_gy = new BigInteger(ecc_param[5], 16);

        this.ecc_gx_fieldelement = new ECFieldElement.Fp(this.ecc_p, this.ecc_gx);
        this.ecc_gy_fieldelement = new ECFieldElement.Fp(this.ecc_p, this.ecc_gy);
        this.ecc_curve = new ECCurve.Fp(this.ecc_p, this.ecc_a, this.ecc_b, null, null);
        this.ecc_point_g = new ECPoint.Fp(this.ecc_curve, this.ecc_gx_fieldelement, this.ecc_gy_fieldelement,false);

        this.ecc_bc_spec = new ECDomainParameters(this.ecc_curve, this.ecc_point_g, this.ecc_n);

        ECKeyGenerationParameters ecc_ecgenparam = new ECKeyGenerationParameters(this.ecc_bc_spec, new SecureRandom());

        this.ecc_key_pair_generator = new ECKeyPairGenerator();
        this.ecc_key_pair_generator.init(ecc_ecgenparam);
    }

    public byte[] sm2GetZ(byte[] userId, ECPoint userKey) {
        SM3Digest sm3 = new SM3Digest();

        int len = userId.length * 8;
        sm3.update((byte)(len >> 8 & 0xFF));
        sm3.update((byte)(len & 0xFF));
        sm3.update(userId, 0, userId.length);

        byte[] p = HexUtil.byteConvert32Bytes(this.ecc_a);
        sm3.update(p, 0, p.length);

        p = HexUtil.byteConvert32Bytes(this.ecc_b);
        sm3.update(p, 0, p.length);

        p = HexUtil.byteConvert32Bytes(this.ecc_gx);
        sm3.update(p, 0, p.length);

        p = HexUtil.byteConvert32Bytes(this.ecc_gy);
        sm3.update(p, 0, p.length);

        p = HexUtil.byteConvert32Bytes(userKey.getXCoord().toBigInteger());
        sm3.update(p, 0, p.length);

        p = HexUtil.byteConvert32Bytes(userKey.getYCoord().toBigInteger());
        sm3.update(p, 0, p.length);

        byte[] md = new byte[sm3.getDigestSize()];
        sm3.doFinal(md, 0);
        return md;
    }

    public void sm2Sign(byte[] md, BigInteger userD, ECPoint userKey, SM2Result sm2Result) {
        BigInteger e = new BigInteger(1, md);
        BigInteger k = null;
        ECPoint kp = null;
        BigInteger r = null;
        BigInteger s = null;
        do
        {
            do {
                AsymmetricCipherKeyPair keypair = this.ecc_key_pair_generator.generateKeyPair();
                ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters)keypair.getPrivate();
                ECPublicKeyParameters ecpub = (ECPublicKeyParameters)keypair.getPublic();
                k = ecpriv.getD();
                kp = ecpub.getQ();

                r = e.add(kp.getXCoord().toBigInteger());
                r = r.mod(this.ecc_n);
            }while ((r.equals(BigInteger.ZERO)) || (r.add(k).equals(this.ecc_n)));

            BigInteger da_1 = userD.add(BigInteger.ONE);
            da_1 = da_1.modInverse(this.ecc_n);

            s = r.multiply(userD);
            s = k.subtract(s).mod(this.ecc_n);
            s = da_1.multiply(s).mod(this.ecc_n);
        }while (s.equals(BigInteger.ZERO));

        sm2Result.r = r;
        sm2Result.s = s;
    }

    public void sm2Verify(byte[] md, ECPoint userKey, BigInteger r, BigInteger s, SM2Result sm2Result) {
        sm2Result.R = null;
        BigInteger e = new BigInteger(1, md);
        BigInteger t = r.add(s).mod(this.ecc_n);
        if (t.equals(BigInteger.ZERO)) {
            return;
        }
        ECPoint x1y1 = this.ecc_point_g.multiply(sm2Result.s);

        x1y1 = x1y1.add(userKey.multiply(t));
        sm2Result.R = e.add(x1y1.getXCoord().toBigInteger()).mod(this.ecc_n);
    }
}
