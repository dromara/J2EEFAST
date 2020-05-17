package com.j2eefast.flowable.bpm.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.*;
import com.j2eefast.flowable.bpm.entity.BpmTaskFromEntity;
import com.j2eefast.flowable.bpm.entity.StartProcessInstanceEntity;
import com.j2eefast.flowable.bpm.service.BpmTaskFromService;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.framework.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.ModelMap;

import java.util.*;

import org.springframework.web.bind.annotation.*;
import com.j2eefast.flowable.bpm.entity.BpmOaFormEntity;
import com.j2eefast.flowable.bpm.service.BpmOaFormService;

/**
 * 办公请假流程表单代码编写例子
 * @author ZhouZhou
 * @date 2020-04-20 22:19
 */
@Controller
@RequestMapping("/bpm/form")
public class BpmOaFormController extends BaseController{

    private String prefix = "modules/bpm/form";

    /**
     * 必须注入实例关联表单服务
     */
    @Autowired
    private BpmTaskFromService bpmTaskFromService;

    @Autowired
    private BpmOaFormService bpmOaFormService;


    /**
     * 定义关联表单申请表单URL对应此处
     */
    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") Long id, ModelMap mmp){
        //通过页面传入的表单ID查询表单关联信息
        BpmTaskFromEntity bpmTaskFrom = bpmTaskFromService.getTaskFromById(id);
        mmp.put("bpmTaskFrom", bpmTaskFrom);
        mmp.put("user", UserUtils.getUserInfo());
        return prefix + "/add";
    }

    /**
     * 表单详情
     * @param taskId
     * @param businessKey
     * @param mmap
     * @return
     */
    @GetMapping("/view")
    public String view(@RequestParam(value="taskId", required=true)String taskId,
                       @RequestParam(value="businessKey", required=true)String businessKey,
                       ModelMap mmap){
        BpmOaFormEntity bpmOaForm = bpmOaFormService.getBpmOaFormById(businessKey);
        mmap.put("bpmOaForm", bpmOaForm);
        mmap.put("taskId", taskId);
        return prefix + "/view";
    }


    /**
     * 发起人员撤回
     * @param businessKey
     * @param mmap
     * @return
     */
    @RequiresPermissions("bpm:form:approval")
    @GetMapping("/revoke")
    public String revoke(@RequestParam(value="businessKey", required=true)String businessKey,
                       ModelMap mmap){
        BpmOaFormEntity bpmOaForm = bpmOaFormService.getBpmOaFormById(businessKey);
        mmap.put("bpmOaForm", bpmOaForm);
        return prefix + "/revoke";
    }

    /**
     * 新增
     */
    @RepeatSubmit
    @BussinessLog(title = "OA请假单", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addBpmOaForm(@Validated BpmOaFormEntity bpmOaForm){
        //校验参数
        ValidatorUtil.validateEntity(bpmOaForm);
        return bpmOaFormService.saveBpmOaForm(bpmOaForm);
    }



    
        /**
     * 修改
     */
    @GetMapping("/edit")
    public String edit(@RequestParam(value="taskId", required=true)String taskId,
                       @RequestParam(value="businessKey", required=true)String businessKey,
                       ModelMap mmap){
        BpmOaFormEntity bpmOaForm = bpmOaFormService.getBpmOaFormById(businessKey);
        mmap.put("bpmOaForm", bpmOaForm);
        mmap.put("taskId", taskId);
        return prefix + "/edit";
    }

    /**
     * 修改保存OA请假单
     */
    @RepeatSubmit
    @RequiresPermissions("bpm:form:edit")
    @BussinessLog(title = "OA请假单", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData editBpmOaForm(BpmOaFormEntity bpmOaForm){
		ValidatorUtil.validateEntity(bpmOaForm);
        return bpmOaFormService.updateBpmOaFormById(bpmOaForm)? success(): error("修改失败!");
    }
    
}
