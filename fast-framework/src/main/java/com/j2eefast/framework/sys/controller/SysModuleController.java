package com.j2eefast.framework.sys.controller;

import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.*;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.framework.utils.Constant;
import com.j2eefast.framework.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.ModelMap;
import java.util.Map;
import java.util.Date;
import org.springframework.web.bind.annotation.*;
import com.j2eefast.framework.sys.entity.SysModuleEntity;
import com.j2eefast.framework.sys.service.SysModuleService;

/**
 * 模块页面控制器
 * @author zhouzhou
 * @date 2020-03-08 21:38
 */
@Controller
@RequestMapping("/sys/module")
public class SysModuleController extends BaseController {
    private String urlPrefix = "modules/sys/module";

    @Autowired
    private SysModuleService sysModuleService;

    @RequiresPermissions("sys:module:view")
    @GetMapping()
    public String module() {
        return urlPrefix + "/module";
    }


    @RequestMapping("/list")
    @RequiresPermissions("sys:module:list")
    @ResponseBody
    public ResponseData list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysModuleService.findPage(params);
        return success(page);
    }
    
    
        
    /**
     * 新增参数配置
     */
    @GetMapping("/add")
    public String add() {
        return urlPrefix + "/add";
    }

    /**
     * 新增保存模块
     */
    @RepeatSubmit
    @RequiresPermissions("sys:module:add")
    @BussinessLog(title = "模块", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addSave(@Validated SysModuleEntity sysModule){
        //校验参数
        ValidatorUtil.validateEntity(sysModule);
        return sysModuleService.save(sysModule)?success(): error("新增失败!");
    }
    
        /**
     * 修改模块 页面
     */
    @RequiresPermissions("sys:module:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap){
        SysModuleEntity sysModule = sysModuleService.getById(id);
        mmap.put("sysModule", sysModule);
        return urlPrefix + "/edit";
    }

    /**
     * 修改保存模块
     */
    @RepeatSubmit
    @RequiresPermissions("sys:module:edit")
    @BussinessLog(title = "模块", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData edit(SysModuleEntity sysModule){
    	
        ValidatorUtil.validateEntity(sysModule);
        return sysModuleService.updateById(sysModule)?success():error("修改失败!");
        
    }
    
        
    /**
     * 删除模块
     */
    @RepeatSubmit
    @BussinessLog(title = "模块", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @RequiresPermissions("sys:module:del")
    @ResponseBody
    public ResponseData delete(Long[] ids) {
    	
    	return sysModuleService.deleteBatchByIds(ids)?success():error("删除失败!");
    	
    }

    /**
     * 模块状态修改
     */
    @BussinessLog(title = "模块", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @RequiresPermissions("sys:module:edit")
    @RequiresRoles(Constant.SU_ADMIN)
    @PostMapping("/status")
    @ResponseBody
    public ResponseData changeStatus(SysModuleEntity sysModule){
        return sysModuleService.updateById(sysModule)? success() : error("修改失败!");
    }
}
