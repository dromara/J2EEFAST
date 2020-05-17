package com.j2eefast.common.core.config;

import cn.hutool.core.util.StrUtil;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.crypto.DesUtils;
import com.j2eefast.common.core.crypto.EnctryptTools;
import com.j2eefast.common.core.crypto.MyEncryptablePropertyDetector;
import com.j2eefast.common.core.utils.HexUtil;
import com.j2eefast.common.core.utils.JasyptUtils;
import com.j2eefast.common.core.utils.ToolUtil;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * <p>yml 文件敏感信息处理类</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-24 20:45
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class MyEncryptablePropertyResolver implements EncryptablePropertyResolver {

    @Value("${fast.encrypt.key: ''}")
    private String key;

    @Override
    public String resolvePropertyValue(String str) {

        if (ToolUtil.isNotEmpty(str)) {

            try{
                //对配置文件加密值进行解密。加密方式可以自定义
                if(StrUtil.startWith(str,MyEncryptablePropertyDetector.ENCODED_PASSWORD_HINT_ENC,true)){
                    str = StrUtil.removeSuffixIgnoreCase(StrUtil.removePrefixIgnoreCase(str,
                            MyEncryptablePropertyDetector.ENCODED_PASSWORD_HINT_ENC),"}");
                    if(ToolUtil.isEmpty(key)){ //AB01950E7FA163AF3323F059F3DF8D87
                        key = ConfigConstant.KEY;
                    }
                    str = JasyptUtils.decyptPwd(str,key);
                }

                if(StrUtil.startWith(str,MyEncryptablePropertyDetector.ENCODED_PASSWORD_HINT_DES,true)){
                    str = StrUtil.removeSuffixIgnoreCase(StrUtil.removePrefixIgnoreCase(str,
                            MyEncryptablePropertyDetector.ENCODED_PASSWORD_HINT_DES),"}");
                    if(ToolUtil.isEmpty(key)){
                        key = ConfigConstant.KEY;
                    }
                    str = EnctryptTools.DesDecode(str,key);
                }

                if(StrUtil.startWith(str,MyEncryptablePropertyDetector.ENCODED_PASSWORD_HINT_SM4,true)){
                    str = StrUtil.removeSuffixIgnoreCase(StrUtil.removePrefixIgnoreCase(str,
                            MyEncryptablePropertyDetector.ENCODED_PASSWORD_HINT_SM4),"}");
                    if(ToolUtil.isEmpty(key)){
                        key = ConfigConstant.KEY;
                    }
                    str = EnctryptTools.SM4Decode(str,key);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return str;
    }



}