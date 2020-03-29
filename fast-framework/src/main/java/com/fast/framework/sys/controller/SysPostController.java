package com.fast.framework.sys.controller;

import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.common.core.enums.BusinessType;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.common.core.utils.ValidatorUtil;
import com.fast.framework.utils.AbstractController;
import com.fast.framework.annotation.RepeatSubmit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.ModelMap;
import java.util.Map;
import java.util.Date;
import org.springframework.web.bind.annotation.*;
import com.fast.framework.sys.entity.SysPostEntity;
import com.fast.framework.sys.service.SysPostService;

/**
 *
 * @ClassName: 岗位信息Controller
 * @Package: com.fast.framework.sys
 * @Description: sys_post
 * @author: ZhouHuan
 * @time 2020-02-28
 */
@Controller
@RequestMapping("/sys/post")
public class SysPostController extends AbstractController
{
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
    public R list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysPostService.queryPage(params);
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
     * 新增保存岗位信息
     */
    @RepeatSubmit
    @RequiresPermissions("sys:post:add")
    @BussinessLog(title = "岗位信息", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public R addSave(@Validated SysPostEntity sysPost)
    {
        //校验参数
        ValidatorUtil.validateEntity(sysPost);
        sysPost.setCreateBy(getUser().getUsername());
        sysPost.setCreateTime(new Date());
        sysPost.setUpdateTime(new Date());
        sysPostService.insertSysPost(sysPost);
        return R.ok();
    }
    
        /**
     * 修改岗位信息 页面
     */
    @RequiresPermissions("sys:post:edit")
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, ModelMap mmap)
    {
        SysPostEntity sysPost = sysPostService.selectSysPostById(postId);
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
    public R editSave(SysPostEntity sysPost)
    {
        ValidatorUtil.validateEntity(sysPost);
        sysPost.setUpdateBy(getLoginName());
        sysPostService.updateSysPost(sysPost);
        return R.ok();
    }
    
        
    /**
     * 删除岗位信息
     */
    @RepeatSubmit
    @BussinessLog(title = "岗位信息", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @RequiresPermissions("sys:post:del")
    @ResponseBody
    public R delete(Long[] ids) {

      sysPostService.deleteBatch(ids);
      return R.ok();
    }
    
}
