package com.fast.framework.sys.controller;

import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.common.core.enums.BusinessType;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.common.core.utils.ToolUtil;
import com.fast.common.core.utils.ValidatorUtil;
import com.fast.framework.utils.AbstractController;
import com.fast.framework.annotation.RepeatSubmit;
import com.fast.framework.utils.Constant;
import com.fast.framework.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.ModelMap;
import java.util.Map;
import java.util.Date;
import org.springframework.web.bind.annotation.*;
import com.fast.framework.sys.entity.SysModuleEntity;
import com.fast.framework.sys.service.SysModuleService;

/**
 * 模块页面控制器
 * @author zhouzhou
 * @date 2020-03-08 21:38
 */
@Controller
@RequestMapping("/sys/module")
public class SysModuleController extends AbstractController {
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
    public R list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysModuleService.findPage(params);
        return R.ok().put("page", page);
    }
    
    
        
    /**
     * 新增参数配置
     */
    @GetMapping("/add")
    public String add()
    {
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
    public R addSave(@Validated SysModuleEntity sysModule)
    {
        //校验参数
        ValidatorUtil.validateEntity(sysModule);
        sysModuleService.save(sysModule);
        return R.ok();
    }
    
        /**
     * 修改模块 页面
     */
    @RequiresPermissions("sys:module:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
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
    public R editSave(SysModuleEntity sysModule){
    	
        ValidatorUtil.validateEntity(sysModule);
        return sysModuleService.updateById(sysModule)?R.ok():R.error();
        
    }
    
        
    /**
     * 删除模块
     */
    @RepeatSubmit
    @BussinessLog(title = "模块", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @RequiresPermissions("sys:module:del")
    @ResponseBody
    public R delete(Long[] ids) {
    	
    	return sysModuleService.deleteBatchByIds(ids)?R.ok():R.error();
    	
    }

    /**
     * 模块状态修改
     */
    @BussinessLog(title = "模块", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @RequiresPermissions("sys:module:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public R changeStatus(SysModuleEntity sysModule){
        if(!ShiroUtils.isPermissions(Constant.SU_ADMIN)){
            return R.error(ToolUtil.message("sys.msg.permissions"));
        }
        return sysModuleService.updateById(sysModule)? R.ok() : R.error();
    }
}
