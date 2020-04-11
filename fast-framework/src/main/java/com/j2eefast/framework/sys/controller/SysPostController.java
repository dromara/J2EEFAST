package com.j2eefast.framework.sys.controller;

import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ValidatorUtil;
import com.j2eefast.framework.annotation.RepeatSubmit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.ModelMap;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import com.j2eefast.framework.sys.entity.SysPostEntity;
import com.j2eefast.framework.sys.service.SysPostService;

/**
 *
 * @ClassName: 岗位信息Controller
 * @Package: com.j2eefast.framework.sys
 * @Description: sys_post
 * @author: zhouzhou
 * @time 2020-02-28
 */
@Controller
@RequestMapping("/sys/post")
public class SysPostController extends BaseController {
    private String urlPrefix = "modules/sys/post";

    @Autowired
    private SysPostService sysPostService;

    @RequiresPermissions("sys:post:view")
    @GetMapping()
    public String post()
    {
        return urlPrefix + "/post";
    }

        
    @RequestMapping("/list")
    @RequiresPermissions("sys:post:list")
    @ResponseBody
    public ResponseData list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysPostService.findPage(params);
        return success(page);
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
     * 新增保存岗位信息
     */
    @RepeatSubmit
    @RequiresPermissions("sys:post:add")
    @BussinessLog(title = "岗位信息", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addSave(@Validated SysPostEntity sysPost){
        //校验参数
        ValidatorUtil.validateEntity(sysPost);
        sysPostService.save(sysPost);
        return success();
    }
    
        /**
     * 修改岗位信息 页面
     */
    @RequiresPermissions("sys:post:edit")
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, ModelMap mmap){
        SysPostEntity sysPost = sysPostService.getById(postId);
        mmap.put("sysPost", sysPost);
        return urlPrefix + "/edit";
    }

    /**
     * 修改保存岗位信息
     */
    @RepeatSubmit
    @RequiresPermissions("sys:post:edit")
    @BussinessLog(title = "岗位信息", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData editSave(SysPostEntity sysPost) {
        ValidatorUtil.validateEntity(sysPost);
        return sysPostService.updateById(sysPost)?success():error("修改失败!");
    }
    
        
    /**
     * 删除岗位信息
     */
    @RepeatSubmit
    @BussinessLog(title = "岗位信息", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @RequiresPermissions("sys:post:del")
    @ResponseBody
    public ResponseData delete(Long[] ids) {
      return  sysPostService.deleteBatchByIds(ids)?success():error("删除失败!");
    }
    
}
