package com.j2eefast.flowable.bpm.controller;

import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.flowable.bpm.service.ProcdefService;
import com.j2eefast.framework.utils.Constant;
import com.j2eefast.framework.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.flowable.idm.api.User;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.ui.common.model.UserRepresentation;

import java.util.Map;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2019-04-14 14:41
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Controller
@RequestMapping("/bpm/model")
public class DeModelController extends BaseController {

	private String urlPrefix = "modules/bpm/deploy";

	@Autowired
	protected ModelService modelService;
	@Autowired
	private ProcdefService procdefService;

	/**
	 * 页面获取用户信息
	 * @return
	 */
	@RequestMapping(value="/account", method = RequestMethod.GET, produces = "application/json")
	@RequiresRoles(Constant.SU_ADMIN)
	@ResponseBody
	public UserRepresentation account() {
		User user=new UserEntityImpl();
		user.setId(UserUtils.getLoginName());
		user.setEmail(UserUtils.getUserInfo().getEmail());
		SecurityUtils.assumeUser(user);
		UserRepresentation userRepresentation = new UserRepresentation();
		userRepresentation.setId(UserUtils.getLoginName());
		userRepresentation.setFullName(UserUtils.getUserInfo().getName());
		userRepresentation.setPrivileges(UserUtils.getUserInfo().getRoleKey());
		return userRepresentation;
	}

	/**
	 * 流程管理页面
	 * @return
	 */
	@GetMapping()
	@RequiresPermissions("bpm:model:view")
	public String modelerIndex(){
		return REDIRECT +"/flowable/modeler/index.html";
	}

	/**
	 * 跳转流程部署页面
	 * @return
	 */
	@RequiresPermissions("bpm:model:deploy")
	@GetMapping("/deployIndex")
	public String deployIndex(@RequestParam("id") String id, ModelMap mmap) {
		mmap.put("model",modelService.getModelRepresentation(id));
		return urlPrefix + "/deploy";
	}
//

	/**
	 * 部署流程
	 * @return
	 */
	@RequiresPermissions("bpm:model:add")
	@RequestMapping(value = "/add" , method = RequestMethod.POST)
	@ResponseBody
	public ResponseData deploy(String modelId, String category) {
		return procdefService.addDeploy(modelId,category);
	}


	@RequestMapping("/list")
	@RequiresPermissions("bpm:model:list")
	@ResponseBody
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = procdefService.findPage(params);
		return success(page);
	}


}
