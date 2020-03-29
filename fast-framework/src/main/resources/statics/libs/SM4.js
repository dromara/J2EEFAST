/**
 * base64js
 */
(function(r){if(typeof exports==="object"&&typeof module!=="undefined"){module.exports=r()}else{if(typeof define===
"function"&&define.amd){define([],r)}else{var e;if(typeof window!=="undefined"){e=window}else{if(typeof global
!=="undefined"){e=global}else{if(typeof self!=="undefined"){e=self}else{e=this}}}e.base64js=r()}}})(function(){
var r,e,t;return function r(e,t,n){function o(i,a){if(!t[i]){if(!e[i]){var u=typeof require=="function"&&require;if(!a&&u){
return u(i,!0)}if(f){return f(i,!0)}var d=new Error("Cannot find module '"+i+"'");throw d.code="MODULE_NOT_FOUND",d}
var c=t[i]={exports:{}};e[i][0].call(c.exports,function(r){var t=e[i][1][r];return o(t?t:r)},c,c.exports,r,e,t,n)}return t[i].exports}
var f=typeof require=="function"&&require;for(var i=0;i<n.length;i++){o(n[i])}return o}({"/":[function(r,e,t){t.byteLength=c;
t.toByteArray=v;t.fromByteArray=s;var n=[];var o=[];var f=typeof Uint8Array!=="undefined"?Uint8Array:Array;
var i="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";for(var a=0,u=i.length;a<u;++a){n[a]=i[a];
o[i.charCodeAt(a)]=a}o["-".charCodeAt(0)]=62;o["_".charCodeAt(0)]=63;function d(r){var e=r.length;if(e%4>0){
throw new Error("Invalid string. Length must be a multiple of 4")}return r[e-2]==="="?2:r[e-1]==="="?1:0}
function c(r){return r.length*3/4-d(r)}function v(r){var e,t,n,i,a;var u=r.length;i=d(r);a=new f(u*3/4-i);t=i>0?u-4:u;
var c=0;for(e=0;e<t;e+=4){n=o[r.charCodeAt(e)]<<18|o[r.charCodeAt(e+1)]<<12|o[r.charCodeAt(e+2)]<<6|o[r.charCodeAt(e+3)];
a[c++]=n>>16&255;a[c++]=n>>8&255;a[c++]=n&255}if(i===2){n=o[r.charCodeAt(e)]<<2|o[r.charCodeAt(e+1)]>>4;a[c++]=n&255}
else{if(i===1){n=o[r.charCodeAt(e)]<<10|o[r.charCodeAt(e+1)]<<4|o[r.charCodeAt(e+2)]>>2;a[c++]=n>>8&255;a[c++]=n&255}}return a}
function l(r){return n[r>>18&63]+n[r>>12&63]+n[r>>6&63]+n[r&63]}function h(r,e,t){var n;var o=[];for(var f=e;f<t;f+=3){
n=(r[f]<<16)+(r[f+1]<<8)+r[f+2];o.push(l(n))}return o.join("")}function s(r){var e;var t=r.length;var o=t%3;var f="";var i=[];
var a=16383;for(var u=0,d=t-o;u<d;u+=a){i.push(h(r,u,u+a>d?d:u+a))}if(o===1){e=r[t-1];f+=n[e>>2];f+=n[e<<4&63];f+="=="}else{if(o===2){
e=(r[t-2]<<8)+r[t-1];f+=n[e>>10];f+=n[e>>4&63];f+=n[e<<2&63];f+="="}}i.push(f);return i.join("")}},{}]},{},[])("/")});


/**
 * 国密SM4加密算法
 * 使用方法
 *      var s4=new SM4Util("16进制字符串Key长度32");
 *      s4.encryptData_ECB("admin");
 *    //返回 BASE64--> tS3na99wYqbF05+KtJaLGw==
 * @author ZhouHuan
 * @Date 2020-01-21
 */
function SM4_Context() {
    this.mode=1;
    this.isPadding = true;
    this.sk = new Array(32);
};

function SM4() {
    this.SM4_ENCRYPT=1;   ////加密
    this.SM4_DECRYPT = 0;  //解密

    var SboxTable = [0xd6,0x90,0xe9,0xfe,0xcc,0xe1,0x3d,0xb7,0x16,0xb6,0x14,0xc2,0x28,0xfb,0x2c,0x05,
        0x2b,0x67,0x9a,0x76,0x2a,0xbe,0x04,0xc3,0xaa,0x44,0x13,0x26,0x49,0x86,0x06,0x99,
        0x9c,0x42,0x50,0xf4,0x91,0xef,0x98,0x7a,0x33,0x54,0x0b,0x43,0xed,0xcf,0xac,0x62,
        0xe4,0xb3,0x1c,0xa9,0xc9,0x08,0xe8,0x95,0x80,0xdf,0x94,0xfa,0x75,0x8f,0x3f,0xa6,
        0x47,0x07,0xa7,0xfc,0xf3,0x73,0x17,0xba,0x83,0x59,0x3c,0x19,0xe6,0x85,0x4f,0xa8,
        0x68,0x6b,0x81,0xb2,0x71,0x64,0xda,0x8b,0xf8,0xeb,0x0f,0x4b,0x70,0x56,0x9d,0x35,
        0x1e,0x24,0x0e,0x5e,0x63,0x58,0xd1,0xa2,0x25,0x22,0x7c,0x3b,0x01,0x21,0x78,0x87,
        0xd4,0x00,0x46,0x57,0x9f,0xd3,0x27,0x52,0x4c,0x36,0x02,0xe7,0xa0,0xc4,0xc8,0x9e,
        0xea,0xbf,0x8a,0xd2,0x40,0xc7,0x38,0xb5,0xa3,0xf7,0xf2,0xce,0xf9,0x61,0x15,0xa1,
        0xe0,0xae,0x5d,0xa4,0x9b,0x34,0x1a,0x55,0xad,0x93,0x32,0x30,0xf5,0x8c,0xb1,0xe3,
        0x1d,0xf6,0xe2,0x2e,0x82,0x66,0xca,0x60,0xc0,0x29,0x23,0xab,0x0d,0x53,0x4e,0x6f,
        0xd5,0xdb,0x37,0x45,0xde,0xfd,0x8e,0x2f,0x03,0xff,0x6a,0x72,0x6d,0x6c,0x5b,0x51,
        0x8d,0x1b,0xaf,0x92,0xbb,0xdd,0xbc,0x7f,0x11,0xd9,0x5c,0x41,0x1f,0x10,0x5a,0xd8,
        0x0a,0xc1,0x31,0x88,0xa5,0xcd,0x7b,0xbd,0x2d,0x74,0xd0,0x12,0xb8,0xe5,0xb4,0xb0,
        0x89,0x69,0x97,0x4a,0x0c,0x96,0x77,0x7e,0x65,0xb9,0xf1,0x09,0xc5,0x6e,0xc6,0x84,
        0x18,0xf0,0x7d,0xec,0x3a,0xdc,0x4d,0x20,0x79,0xee,0x5f,0x3e,0xd7,0xcb,0x39,0x48];

    var FK = [ 0xa3b1bac6, 0x56aa3350, 0x677d9197, 0xb27022dc ];

    var CK = [ 0x00070e15,0x1c232a31,0x383f464d,0x545b6269,
        0x70777e85,0x8c939aa1,0xa8afb6bd,0xc4cbd2d9,
        0xe0e7eef5,0xfc030a11,0x181f262d,0x343b4249,
        0x50575e65,0x6c737a81,0x888f969d,0xa4abb2b9,
        0xc0c7ced5,0xdce3eaf1,0xf8ff060d,0x141b2229,
        0x30373e45,0x4c535a61,0x686f767d,0x848b9299,
        0xa0a7aeb5,0xbcc3cad1,0xd8dfe6ed,0xf4fb0209,
        0x10171e25,0x2c333a41,0x484f565d,0x646b7279 ];

    this.ROTL1 = function (x,y){
        return x<<y|x>>>(32-y);
    };

    this.BYTESUB = function (A)
    {
        return (SboxTable[A>>>24&0xFF]&0xFF)<<24|(SboxTable[A>>>16&0xFF]&0xFF)<<16|(SboxTable[A>>>8&0xFF]&0xFF)<<8|(SboxTable[A&0xFF]&0xFF);
    };


    this.L1=function(B)
    {
        return B^this.ROTL1(B,2)^this.ROTL1(B,10)^this.ROTL1(B,18)^this.ROTL1(B,24);
    };

    this.L2=function(B)
    {
        return B^this.ROTL1(B,13)^this.ROTL1(B,23);
    }
    this.sm4_setkey_enc = function(ctx, key){
        if (ctx == null) {
            alert("ctx is null!");
            return false;
        }
        if (key == null || key.length != 16){
            alert("key error!");
            return false;
        }
        ctx.mode = this.SM4_ENCRYPT;
        this.sm4_setkey(ctx.sk, key);

    };

    this.sm4_setkey = function(rk, Key){
        var r,mid;
        var x= new Array(4);
        var tmp = new Array(4);
        for(var i=0;i<4;i++)
        {
            tmp[0]=Key[0+4*i]&0xFF;
            tmp[1]=Key[1+4*i]&0xff;
            tmp[2]=Key[2+4*i]&0xff;
            tmp[3]=Key[3+4*i]&0xff;
            x[i]=tmp[0]<<24|tmp[1]<<16|tmp[2]<<8|tmp[3];
        }
        x[0]^=0xa3b1bac6;
        x[1]^=0x56aa3350;
        x[2]^=0x677d9197;
        x[3]^=0xb27022dc;
        for(var r=0;r<32;r+=4)
        {
            mid=x[1]^x[2]^x[3]^CK[r+0];
            mid=this.BYTESUB(mid);
            rk[r+0]=x[0]^=this.L2(mid);

            mid=x[2]^x[3]^x[0]^CK[r+1];
            mid=this.BYTESUB(mid);
            rk[r+1]=x[1]^=this.L2(mid);

            mid=x[3]^x[0]^x[1]^CK[r+2];
            mid=this.BYTESUB(mid);
            rk[r+2]=x[2]^=this.L2(mid);

            mid=x[0]^x[1]^x[2]^CK[r+3];
            mid=this.BYTESUB(mid);
            rk[r+3]=x[3]^=this.L2(mid);
        }

    }
    this.padding=function(input,mode){
        if (input == null){
            return null;
        }
        var ret =  null;
        if (mode == this.SM4_ENCRYPT){
            var p = parseInt(16 - input.length % 16);
            ret = input.slice(0);
            for (var i = 0; i < p; i++){
                ret[input.length + i] = 0;
            }
        }else{
            var p = input[input.length - 1];
            ret=input.slice(0,input.length - p);
        }
        return ret;
    }

    this.sm4_one_round=function(rk, Input, Output){
        var r,mid,x0,x1,x2,x3;
        var x= new Array(4);
        var tmp = new Array(4);
        for(var i=0;i<4;i++)
        {
            tmp[0]=Input[0+4*i]&0xff;
            tmp[1]=Input[1+4*i]&0xff;
            tmp[2]=Input[2+4*i]&0xff;
            tmp[3]=Input[3+4*i]&0xff;
            x[i]=tmp[0]<<24|tmp[1]<<16|tmp[2]<<8|tmp[3];
        }
        for(var r=0;r<32;r+=4)
        {
            mid=x[1]^x[2]^x[3]^rk[r+0];
            mid=this.BYTESUB(mid);
            x[0]=x[0]^this.L1(mid);

            mid=x[2]^x[3]^x[0]^rk[r+1];
            mid=this.BYTESUB(mid);
            x[1]=x[1]^this.L1(mid);

            mid=x[3]^x[0]^x[1]^rk[r+2];
            mid=this.BYTESUB(mid);
            x[2]=x[2]^this.L1(mid);

            mid=x[0]^x[1]^x[2]^rk[r+3];
            mid=this.BYTESUB(mid);
            x[3]=x[3]^this.L1(mid);
        }

        for(var j=0;j<16;j+=4)
        {
            Output[j]  = (x[3-j/4]>>>24&0xFF);
            Output[j+1]= (x[3-j/4]>>>16&0xFF);
            Output[j+2]= (x[3-j/4]>>>8&0xFF);
            Output[j+3]= (x[3-j/4]&0xFF);
        }

    }



    this.sm4_crypt_ecb=function(ctx,input){
        if (input == null){
            alert("input is null!");
        }
        if ((ctx.isPadding) && (ctx.mode == this.SM4_ENCRYPT)){
            input = this.padding(input, this.SM4_ENCRYPT);
        }
        console.log("input:"+input + " ctx.sk" + ctx.sk);
        var i=0;
        var length = input.length;
        var bous = new Array();
        for(; length > 0; length -= 16)
        {
            var out = new Array(16);
            var ins=input.slice(i*16,(16*(i+1)));
            this.sm4_one_round(ctx.sk, ins, out)
            bous=bous.concat(out);
            i++;
        }

        var output = bous;
        if (ctx.isPadding && ctx.mode == this.SM4_DECRYPT)
        {
            output = this.padding(output, this.SM4_DECRYPT);
        }
        for(var i=0;i<output.length;i++){
            if(output[i]<0){
                output[i]=output[i]+256;
            }
        }
        return output;
    }

    this.sm4_crypt_cbc=function(ctx, iv, input){
        if (iv == null || iv.length != 16) {
            alert("iv error!");
        }

        if (input == null){
            alert("input is null!");
        }

        if (ctx.isPadding && ctx.mode == this.SM4_ENCRYPT) {
            input = this.padding(input, this.SM4_ENCRYPT);
        }

        var i = 0;
        var length = input.length;
        var bous =new Array();
        if (ctx.mode == this.SM4_ENCRYPT){
            var k=0;
            for(; length > 0; length -= 16){
                var out = new Array(16);
                var out1 = new Array(16);
                var ins=input.slice(k*16,(16*(k+1)));

                for (i = 0; i < 16; i++)
                {
                    out[i] = (ins[i] ^ iv[i]);
                }
                this.sm4_one_round(ctx.sk, out, out1);
                iv=out1.slice(0,16);
                bous=bous.concat(out1);
                k++;
            }
        }
        else
        {
            var temp = [];
            var k=0;
            for(; length > 0; length -= 16)
            {
                var out = new Array(16);
                var out1 = new Array(16);
                var ins=input.slice(k*16,(16*(k+1)));
                temp=ins.slice(0,16);
                sm4_one_round(ctx.sk, ins, out);
                for (i = 0; i < 16; i++)
                {
                    out1[i] = (out[i] ^ iv[i]);
                }
                iv=temp.slice(0,16);
                bous=bous.concat(out1);
                k++;
            }
        }

        var output = bous;
        if (ctx.isPadding && ctx.mode == this.SM4_DECRYPT)
        {
            output = this.padding(output, this.SM4_DECRYPT);
        }

        for(var i=0;i<output.length;i++){
            if(output[i]<0){
                output[i]=output[i]+256;
            }
        }
        return output;
    }
}


function SM4Util(key) {
    this.secretKey=key;
    this.iv = "";
    this.hexString = false;

    /**
     * 加密ECB
     * @param plainText
     * @returns {null|string | * | number}
     */
    this.encryptData_ECB=function(plainText){
        try{
            var sm4 = new SM4();
            var ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = sm4.SM4_ENCRYPT;
            var keyBytes= HexStr2Bytes(this.secretKey);
            sm4.sm4_setkey_enc(ctx, keyBytes);
            var encrypted = sm4.sm4_crypt_ecb(ctx, stringToByte(plainText));
            var cipherText = base64js.fromByteArray(encrypted);
            if (cipherText != null && cipherText.trim().length > 0)
            {
                cipherText.replace(/(\s*|\t|\r|\n)/g, "");
            }
            return cipherText;
        }catch (e){
            console.error(e);
            return null;
        }
    }


    stringToByte=function(str) {
        var bytes = new Array();
        var len, c;
        len = str.length;
        for(var i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if(c >= 0x010000 && c <= 0x10FFFF) {
                bytes.push(((c >> 18) & 0x07) | 0xF0);
                bytes.push(((c >> 12) & 0x3F) | 0x80);
                bytes.push(((c >> 6) & 0x3F) | 0x80);
                bytes.push((c & 0x3F) | 0x80);
            } else if(c >= 0x000800 && c <= 0x00FFFF) {
                bytes.push(((c >> 12) & 0x0F) | 0xE0);
                bytes.push(((c >> 6) & 0x3F) | 0x80);
                bytes.push((c & 0x3F) | 0x80);
            } else if(c >= 0x000080 && c <= 0x0007FF) {
                bytes.push(((c >> 6) & 0x1F) | 0xC0);
                bytes.push((c & 0x3F) | 0x80);
            } else {
                bytes.push(c & 0xFF);
            }
        }
        return bytes;
    }

     HexStr2Bytes = function (str)
    {

        var pos = 0;

        var len = str.length;

        if(len %2 != 0)

        {

            return null;

        }

        len /= 2;

        var hexA = new Array();

        for(var i=0; i<len; i++)

        {

            var s = str.substr(pos, 2);

            var v = parseInt(s, 16);

            hexA.push(v);

            pos += 2;

        }

        return hexA;

    }



    byteToString=function(arr) {
        if(typeof arr === 'string') {
            return arr;
        }
        var str = '',
            _arr = arr;
        for(var i = 0; i < _arr.length; i++) {
            var one = _arr[i].toString(2),
                v = one.match(/^1+?(?=0)/);
            if(v && one.length == 8) {
                var bytesLength = v[0].length;
                var store = _arr[i].toString(2).slice(7 - bytesLength);
                for(var st = 1; st < bytesLength; st++) {
                    store += _arr[st + i].toString(2).slice(2);
                }
                str += String.fromCharCode(parseInt(store, 2));
                i += bytesLength - 1;
            } else {
                str += String.fromCharCode(_arr[i]);
            }
        }
        return str;
    }
};