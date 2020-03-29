package com.fast.system.poi.controller;

import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.common.core.enums.BusinessType;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.framework.utils.AbstractController;
import com.fast.common.core.utils.ValidatorUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.ModelMap;
import java.util.Map;
import java.util.Date;
import org.springframework.web.bind.annotation.*;
import com.fast.system.poi.entity.SysPoicEntity;
import com.fast.system.poi.service.SysPoicService;

/**
 *
 * @ClassName: 报信息Controller
 * @Package: com.fast.system.poi
 * @Description: sys_poi
 * @author: ZhouHuan
 * @time 2020-01-08
 
 *
 */
@Controller
@RequestMapping("/sys/poi")
public class SysPoicController extends AbstractController
{
    private String urlPrefix = "modules/poi";

    @Autowired
    private SysPoicService sysPoicService;

    @RequiresPermissions("sys:poi:view")
    @GetMapping()
    public String poi()
    {
        return urlPrefix + "/poi";
    }

        
    @RequestMapping("/list")
    @RequiresPermissions("sys:poi:list")
    @ResponseBody
    public R list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysPoicService.queryPage(params);
        return R.ok().put("page", page);
    }
    
    
    
    
    }
