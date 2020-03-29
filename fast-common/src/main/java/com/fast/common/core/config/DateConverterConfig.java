package com.fast.common.core.config;

import cn.hutool.core.date.DateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * <p>全局handler前日期统一处理</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-27 09:23
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
@Component
public class DateConverterConfig implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        String value = source.trim();
        if ("".equals(value)) {
            return null;
        }
        return DateUtil.parse(source);
    }

}
