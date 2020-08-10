package com.j2eefast.modules.demo.contorller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import com.j2eefast.framework.utils.Constant;
import lombok.Data;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 前端演示Demo
 * @author huanzhou
 * @Date 2020-06-19
 */
@Controller
@RequestMapping("/sys/demo")
public class SysDemoController extends BaseController {

    private String urlPrefix = "modules/sys/demo";

    /**
     * 前端模板引擎说明
     * @param mmap
     * @return
     */
    @RequiresPermissions("sys:demo:mode")
    @GetMapping("/mode")
    public String mode(ModelMap mmap) {
        mmap.put("mode",FileUtil.readUtf8String(Constant.BASE_WEB_HTML +urlPrefix+ "/mode.txt"));
        return urlPrefix + "/mode";
    }

    @RequiresPermissions("sys:demo:component")
    @GetMapping("/component")
    public String component() {
        return urlPrefix + "/component";
    }

    /**
     * 创建表单视图
     * @return
     */
    @RequiresPermissions("sys:demo:addfrom")
    @GetMapping("/addfrom")
    public String addfrom() {
        return urlPrefix + "/addfrom";
    }

    @GetMapping("/test")
    public String test() {
        return urlPrefix + "/test";
    }


}