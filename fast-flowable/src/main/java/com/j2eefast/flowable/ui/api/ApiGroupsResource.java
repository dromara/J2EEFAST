package com.j2eefast.flowable.ui.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.flowable.bpm.service.GroupService;
import com.j2eefast.flowable.bpm.service.UserService;
import com.j2eefast.framework.sys.entity.SysRoleEntity;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import com.j2eefast.framework.sys.service.SysRoleService;
import com.j2eefast.framework.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.flowable.ui.common.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>候选组,职位</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-17 22:48
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@RestController
@RequestMapping(value = "/flowable/app")
@Api("BPM前端接口")
@Slf4j
public class ApiGroupsResource {

	@Autowired
	private GroupService gupService;
	@Autowired
	private UserService userService;
	/**
	 * 获取角色
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "/rest/editor-groups", method = RequestMethod.GET, produces = {"application/json"})
	@ApiOperation("获取角色")
	public ResultListDataRepresentation getGroups(@RequestParam(required = false, value = "filter") String filter) {
		return new ResultListDataRepresentation(gupService.findList(filter));
	}


	/**
	 * 获取指派人
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "/rest/editor-users", method = RequestMethod.GET)
	@ApiOperation("获取指派人")
	public ResultListDataRepresentation getUsers(@RequestParam(value = "filter", required = false) String filter) {

		log.info("filter:" + filter);
		//TODO 后期再设置权限问题
		return new ResultListDataRepresentation(userService.findList(filter));
	}

}
