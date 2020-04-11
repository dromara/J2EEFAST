package com.j2eefast.common.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 远程地址查询
 * @author zhouzhou
 * @date 2017-03-12 16:41
 */
public class AddressUtil {
	
	 private static final Logger 					LOG 					= LoggerFactory.getLogger(AddressUtil.class);
     public static final String IP_URL =  "http://api.map.baidu.com/location/ip?ak=gRhqOOqPOQzvM8nMRnVoQswejvggglqY&ip={}&coor=bd09ll";
     public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        // 内网不查询
        if (NetUtil.isInnerIP(ip))
        {
            return "内网IP";
        }
        JSONObject obj;
        try
        {
            System.err.println(StrUtil.format(IP_URL, ip));
            HttpResponse body = HttpRequest.get(StrUtil.format(IP_URL, ip)).charset("GBK").execute();

            if (StrUtil.isBlank(body.body()))
            {
                LOG.error("获取地理位置异常 {}", ip);
                return address;
            }
            obj = JSONUtil.parseObj(body.body());
            //{"address":"CN|上海|上海|None|CHINANET|0|0","content":{"address_detail":{"province":"上海市","city":"上海市","street":"","district":"","street_number":"","city_code":289},"address":"上海市","point":{"x":"121.48789949","y":"31.24916171"}},"status":0}
            int error_code = obj.getInt("status",-1);
            if(error_code == 0) {
                JSONObject data = obj.getJSONObject("content").getJSONObject("address_detail");
                String country = obj.getStr("address", "CN|上海|上海|None|CHINANET|0|0").split("\\|")[0];
                String province = data.getStr("province", "上海");
                String city = data.getStr("city", "上海市");
                //String Isp = data.getStr("Isp", "联通");
                address = StrUtil.format("{} {}-{}", country,province,city);
            }else {
                LOG.error("获取地理位置异常 -->", obj.toString());
            }
        }
        catch (Exception e)
        {
            LOG.error("获取地理位置异常 {}", ip);
        }
        return address;
    }
}
