package com.j2eefast.flowable.bpm.controller;

import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.core.utils.ValidatorUtil;
import com.j2eefast.flowable.bpm.entity.BpmProcessDefinitionEntity;
import com.j2eefast.flowable.bpm.entity.BpmTaskFromEntity;
import com.j2eefast.flowable.bpm.service.BpmTaskFromService;
import com.j2eefast.flowable.bpm.service.ProcdefService;
import com.j2eefast.framework.annotation.RepeatSubmit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>流程定义</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-19 00:02
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Controller
@RequestMapping("/bpm/procdef")
public class ProcdefController extends BaseController {

	private String urlPrefix = "modules/bpm/procdef";

	@Autowired
	private ProcdefService procdefService;
	@Autowired
	private BpmTaskFromService bpmTaskFromService;

	@RequestMapping("/list")
	@RequiresPermissions("bpm:procdef:list")
	@ResponseBody
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = procdefService.findPage(params);
		return success(page);
	}

	@RequiresPermissions("bpm:procdef:view")
	@GetMapping()
	public String procdef() {
		return urlPrefix + "/procdef";
	}

	@GetMapping("/preview/{id}")
	public String preview(@PathVariable("id") String id, ModelMap mmap) {
		mmap.put("id",id);
		return  urlPrefix + "/view";
	}



	/**
	 * 删除流程定义
	 */
	@RepeatSubmit
	@BussinessLog(title = "流程定义", businessType = BusinessType.DELETE)
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@RequiresPermissions("bpm:procdef:del")
	@ResponseBody
	public ResponseData deleteDeployment(String deploymentId) {
		return procdefService.deleteDeployment(deploymentId)?success():error("删除失败!");
	}


	@RequiresPermissions("bpm:procdef:xmlview")
	@GetMapping("/xml/{deploymentId}")
	public String depXml(@PathVariable("deploymentId") String deploymentId, ModelMap mmap){
		ResponseData responseData = procdefService.getDeploymentXml(deploymentId);
		if(ToolUtil.isSuccess(responseData)){
			mmap.put("depXml", responseData.get("data"));
		}else{
			mmap.put("depXml", responseData.get("msg"));
		}
		return urlPrefix + "/xmlView";
	}

	@RequiresPermissions("bpm:procdef:download")
	@BussinessLog(title = "代码生成", businessType = BusinessType.GENCODE)
	@GetMapping("/download/{deploymentId}")
	public void download(@PathVariable("deploymentId") String deploymentId) throws IOException{
		procdefService.downloadXml(getHttpServletResponse(),getHttpServletRequest(), deploymentId);
	}


	/**
	 * 流程定义关联表单
	 * @param deploymentId
	 * @param mmap
	 * @return
	 */
	@RequiresPermissions("bpm:procdef:taskfrom")
	@GetMapping("/taskfrom/{deploymentId}")
	public String taskfrom(@PathVariable("deploymentId") String deploymentId, ModelMap mmap){
		BpmTaskFromEntity bpmTaskFrom = bpmTaskFromService.getByDeploymentId(deploymentId);
		BpmProcessDefinitionEntity bpmProcess = procdefService.getById(deploymentId);
		if(ToolUtil.isNotEmpty(bpmTaskFrom)){
			mmap.put("bpmProcess",bpmProcess);
			mmap.put("bpmTaskFrom",bpmTaskFrom);
			return urlPrefix + "/editFrom";
		}else{
			mmap.put("bpmProcess",bpmProcess);
			return urlPrefix + "/addFrom";
		}
	}


	@RepeatSubmit
	@BussinessLog(title = "流程定义关联表单", businessType = BusinessType.INSERT)
	@RequestMapping(value = "/taskfrom/add", method = RequestMethod.POST)
	@RequiresPermissions("bpm:taskfrom:add")
	@ResponseBody
	public ResponseData taskFromAdd(BpmTaskFromEntity bpmTaskFrom){
		//校验参数
		ValidatorUtil.validateEntity(bpmTaskFrom);
		return bpmTaskFromService.add(bpmTaskFrom)?success():error("新增失败!");
	}

	@RepeatSubmit
	@BussinessLog(title = "流程定义关联表单", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/taskfrom/edit", method = RequestMethod.POST)
	@RequiresPermissions("bpm:taskfrom:add")
	@ResponseBody
	public ResponseData taskFromEdit(BpmTaskFromEntity bpmTaskFrom){
		//校验参数
		ValidatorUtil.validateEntity(bpmTaskFrom);
		return bpmTaskFromService.updateTaskFrom(bpmTaskFrom)?success():error("新增失败!");
	}


	/**
	 * 挂起\激活流程定义
	 */
	@RepeatSubmit
	@BussinessLog(title = "流程定义", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/status", method = RequestMethod.POST)
	@RequiresPermissions("bpm:procdef:status")
	@ResponseBody
	public ResponseData saProcessInstanceById(String deploymentId,int status){
		return procdefService.suspendOrActivateProcessDefinitionById(deploymentId,status)?success():error("修改状态失败!");
	}

}
