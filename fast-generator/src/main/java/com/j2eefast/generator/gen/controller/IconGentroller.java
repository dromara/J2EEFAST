package com.j2eefast.generator.gen.controller;

import com.j2eefast.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @ClassName: IconGentroller
 * @Package: com.j2eefast.generator.gen.controller
 * @Description: (用一句话描述该文件做什么)
 * @author: zhouzhou Emall:18774995071@163.com
 * @time 2020/2/5 19:54
 * @version V1.0
 
 *
 */
@Controller
@RequestMapping("/tool/icon")
public class IconGentroller  extends BaseController {

    private String urlPrefix = "modules/tool/icon";

    @RequiresPermissions("tool:icon:view")
    @GetMapping()
    public String icon()
    {
        return urlPrefix + "/icon";
    }
}
