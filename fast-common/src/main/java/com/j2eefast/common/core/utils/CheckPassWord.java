package com.j2eefast.common.core.utils;

import com.j2eefast.common.core.enums.SafeLevel;

/**
 * 简单密码安全级别校验
 * @author zhouzhou
 * @date 2020-03-12 21:55
 */
public class CheckPassWord {
	
	public static SafeLevel getPwdSecurityLevel(String pPasswordStr) {

        SafeLevel safeLevel = SafeLevel.VERY_WEAK;
        if (pPasswordStr == null) {
            return safeLevel;
        }
        int grade = 0;
        int index = 0;
        char[] pPsdChars = pPasswordStr.toCharArray();

        int numIndex = 0;
        int sLetterIndex = 0;
        int lLetterIndex = 0;
        int symbolIndex = 0;

        for (char pPsdChar : pPsdChars) {
            int ascll = pPsdChar;
            /*
             * 数字 48-57 A-Z 65 - 90 a-z 97 - 122 !"#$%&'()*+,-./ (ASCII码：33~47)
             * :;<=>?@ (ASCII码：58~64) [\]^_` (ASCII码：91~96) {|}~
             * (ASCII码：123~126)
             */
            if (ascll >= 48 && ascll <= 57) {
                numIndex++;
            } else if (ascll >= 65 && ascll <= 90) {
                lLetterIndex++;
            } else if (ascll >= 97 && ascll <= 122) {
                sLetterIndex++;
            } else if ((ascll >= 33 && ascll <= 47)
                    || (ascll >= 58 && ascll <= 64)
                    || (ascll >= 91 && ascll <= 96)
                    || (ascll >= 123 && ascll <= 126)) {
                symbolIndex++;
            }
        }
        /*
         * 一、密码长度: 5 分: 小于等于 4 个字符 10 分: 5 到 7 字符 25 分: 大于等于 8 个字符
         */
        if (pPsdChars.length <= 4) {
            index = 5;
        } else if (pPsdChars.length <= 7) {
            index = 10;
        } else {
            index = 25;
        }
        grade += index;

        /*
         * 二、字母: 0 分: 没有字母 10 分: 全都是小（大）写字母 20 分: 大小写混合字母
         */
        if (lLetterIndex == 0 && sLetterIndex == 0) {
            index = 0;
        } else if (lLetterIndex != 0 && sLetterIndex != 0) {
            index = 20;
        } else {
            index = 10;
        }
        grade += index;
        /*
         * 三、数字: 0 分: 没有数字 10 分: 1 个数字 20 分: 大于 1 个数字
         */
        if (numIndex == 0) {
            index = 0;
        } else if (numIndex == 1) {
            index = 10;
        } else {
            index = 20;
        }
        grade += index;

        /*
         * 四、符号: 0 分: 没有符号 10 分: 1 个符号 25 分: 大于 1 个符号
         */
        if (symbolIndex == 0) {
            index = 0;
        } else if (symbolIndex == 1) {
            index = 10;
        } else {
            index = 25;
        }
        grade += index;
        /*
         * 五、奖励: 2 分: 字母和数字 3 分: 字母、数字和符号 5 分: 大小写字母、数字和符号
         */
        if ((sLetterIndex != 0 || lLetterIndex != 0) && numIndex != 0) {
            index = 2;
        } else if ((sLetterIndex != 0 || lLetterIndex != 0) && numIndex != 0
                && symbolIndex != 0) {
            index = 3;
        } else if (sLetterIndex != 0 && lLetterIndex != 0 && numIndex != 0
                && symbolIndex != 0) {
            index = 5;
        }
        grade += index;

        /*
         * 最后的评分标准: >= 90: 非常安全 >= 80: 安全（Secure） >= 70: 非常强 >= 60: 强（Strong） >=
         * 50: 一般（Average） >= 25: 弱（Weak） >= 0: 非常弱
         */
        if(grade >=90){
        	safeLevel = SafeLevel.VERY_SECURE;
        }else if(grade >= 80){
        	safeLevel = SafeLevel.SECURE;
        }else if(grade >= 70){
        	safeLevel = SafeLevel.VERY_STRONG;
        }else if(grade >= 60){
        	safeLevel = SafeLevel.STRONG;
        }else if(grade >= 50){
        	safeLevel = SafeLevel.AVERAGE;
        }else if(grade >= 25){
        	safeLevel = SafeLevel.WEAK;
        }else if(grade >= 0){
        	safeLevel = SafeLevel.VERY_WEAK;
        }
        return safeLevel;
    }
}
