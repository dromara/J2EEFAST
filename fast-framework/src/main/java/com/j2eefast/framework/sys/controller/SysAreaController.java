package com.j2eefast.framework.sys.controller;

import com.j2eefast.common.core.base.entity.Ztree;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.*;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;
import java.util.Date;
import org.springframework.web.bind.annotation.*;
import com.j2eefast.framework.sys.entity.SysAreaEntity;
import com.j2eefast.framework.sys.service.SysAreaService;

/**
 * 行政区域页面控制器
 * @author ZhouZhou
 * @date 2020-06-04 23:52
 */
@Controller
@RequestMapping("/sys/area")
public class SysAreaController extends BaseController{

    private String prefix = "modules/sys/area";
    @Autowired
    private SysAreaService sysAreaService;

    @RequiresPermissions("sys:area:view")
    @GetMapping()
    public String area(){
        return prefix + "/area";
    }
        
    @RequestMapping("/list")
    @RequiresPermissions("sys:area:list")
    @ResponseBody
    public ResponseData list(@RequestParam Map<String, Object> params,SysAreaEntity sysAreaEntity) {
		List<SysAreaEntity> list = sysAreaService.selectList(sysAreaEntity);
        return success().put("list", list);
    }

    @RequestMapping("/treeData")
    @RequiresPermissions("sys:area:tree")
    @ResponseBody
    public ResponseData treeData() {
        List<Ztree> list = sysAreaService.getAllAreaZtree();
        return success().put("list", list);
    }

    @RequestMapping("/load")
    @RequiresPermissions("sys:area:list")
    @ResponseBody
    public ResponseData load(@RequestParam Map<String, Object> params) {
        PageUtil page =  sysAreaService.findSelectPage(params);
        return ToolUtil.isEmpty(page)?error("数据异常"):success(page);
    }
}
