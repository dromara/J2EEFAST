package com.j2eefast.common.ueditor;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.j2eefast.common.config.service.SysConfigService;
import com.j2eefast.common.core.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;

/**
 * <p>自定义百度富文本控制类</p>
 *
 * @author: zhouzhou
 * @date: 2019-03-29 17:22
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Controller
@RequestMapping({"/file/ueditor/"})
public class UeditorController{

    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping({"/config"})
    @ResponseBody
    public void config(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            String rootPth = sysConfigService.getParamValue("SYS_CONFIG_PROFILE");
            //rootPth = JSON.parseObject(rootPth).getString("paramValue");
            String exec = new ActionEnter(request, rootPth,"config").exec();
            System.err.println(exec);
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
