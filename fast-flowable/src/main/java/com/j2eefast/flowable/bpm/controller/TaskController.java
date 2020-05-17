package com.j2eefast.flowable.bpm.controller;

import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.flowable.bpm.entity.CommentEntity;
import com.j2eefast.flowable.bpm.entity.CompleteTaskEntity;
import com.j2eefast.flowable.bpm.entity.RevokeProcessEntity;
import com.j2eefast.flowable.bpm.service.FlowableCommentService;
import com.j2eefast.flowable.bpm.service.IFlowableTaskService;
import com.j2eefast.flowable.bpm.service.impl.FlowableProcessInstanceServiceImpl;
import com.j2eefast.framework.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>任务控制类</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-25 14:43
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Controller
@RequestMapping("/bpm/task")
public class TaskController extends BaseController {

	private String urlPrefix = "modules/bpm/task";

	@Autowired
	private IFlowableTaskService flowableTaskService;
	@Autowired
	private FlowableCommentService flowableCommentService;
	@Autowired
	private FlowableProcessInstanceServiceImpl  flowableProcessInstanceService;
	@RequiresPermissions("bpm:task:view")
	@GetMapping("/applaying")
	public String applyingTask(){
		return urlPrefix + "/applaying";
	}

	@RequiresPermissions("bpm:task:view")
	@GetMapping("/applyed")
	public String applyedTask(){
		return urlPrefix + "/applyed";
	}


	/**
	 * 查询待办任务
	 * @param params
	 * @return
	 */
	@RequestMapping("/applaying/list")
	@RequiresPermissions("bpm:task:list")
	@ResponseBody
	public ResponseData applayingList(@RequestParam Map<String, Object> params) {
		params.put("userId", UserUtils.getUserIdToStr());
		PageUtil page = flowableTaskService.findApplyingTasksPage(params);
		return success(page);
	}


	/**
	 * 查询已办任务
	 * @param params
	 * @return
	 */
	@RequestMapping("/applyed/list")
	@RequiresPermissions("bpm:task:list")
	@ResponseBody
	public ResponseData applyedList(@RequestParam Map<String, Object> params) {
		if(UserUtils.isSupAdmin()){
			params.put("userId", "");
		}else{
			params.put("userId", UserUtils.getUserIdToStr());
		}
		PageUtil page = flowableTaskService.findApplyedTasksPage(params);
		return success(page);
	}





	/**
	 * 审批任务
	 *
	 * @param params 参数
	 * @return
	 */
	@PostMapping(value = "/complete")
	@BussinessLog(title = "流程任务", businessType = BusinessType.APPROVAL)
	@RequiresPermissions("bpm:task:approval")
	@ResponseBody
	public ResponseData complete(CompleteTaskEntity params) {
		boolean flag = flowableTaskService.isSuspended(params.getProcessInstanceId());
		if (flag){
			params.setType("SP");
			params.setUserId(UserUtils.getUserIdToStr());
			return flowableTaskService.complete(params);
		}else{
			return error("流程已经挂起,请联系管理员!");
		}
	}

	/**
	 * 实例id获取审批意见列表
	 * @param params
	 * @return
	 */
	@PostMapping(value = "/comment")
	@RequiresPermissions("bpm:task:comment")
	@ResponseBody
	public ResponseData comment(@RequestParam Map<String, Object> params) {
		List<CommentEntity> list = flowableCommentService.getFlowCommentVosByProcessInstanceId((String) params.get("processInstanceId"));
		return success(new PageUtil(list,list.size(),50,1));
	}


	/**
	 * 撤回
	 * @param params
	 * @return
	 */
	@PostMapping(value = "/revoke")
	@BussinessLog(title = "流程任务", businessType = BusinessType.REVOKE)
	@RequiresPermissions("bpm:task:revoke")
	@ResponseBody
	public ResponseData revoke(RevokeProcessEntity params) {
		params.setUserId(UserUtils.getUserIdToStr());
		return flowableProcessInstanceService.revokeProcess(params);
	}

}
