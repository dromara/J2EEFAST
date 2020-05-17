package com.j2eefast.modules.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.j2eefast.common.core.base.entity.Ztree;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.framework.sys.entity.SysDeptEntity;
import com.j2eefast.framework.sys.service.SysDeptService;
import com.j2eefast.framework.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.j2eefast.common.core.utils.MapUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.core.utils.ValidatorUtil;
import com.j2eefast.framework.sys.entity.SysCompEntity;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import com.j2eefast.framework.sys.service.SysCompDeptService;
import com.j2eefast.framework.sys.service.SysCompService;
import com.j2eefast.framework.sys.service.SysUserService;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.framework.utils.Constant;

/**
 * 公司管理控制器
 * @author zhouzhou
 * @date 2018-12-05 09:15
 */
@Controller
@RequestMapping("/sys/comp")
public class SysCompController extends BaseController {

	private String urlPrefix = "modules/sys/comp";

	@Autowired
	private SysCompService sysCompService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysCompDeptService sysCompDeptService;
	

	@Autowired
	private SysDeptService sysDeptService;




	@RequiresPermissions("sys:comp:view")
	@GetMapping()
	public String compData() {
		return urlPrefix + "/comp";
	}


	/**
	 * 修改
	 */
	@GetMapping("/edit/{compId}")
	public String edit(@PathVariable("compId") Long compId, ModelMap mmap){
		mmap.put("comp",  sysCompService.findCompById(compId));
		Map<String, Object> params = new HashMap<>(1);
		params.put("compId",compId);
		List<SysDeptEntity> list = sysDeptService.findByDeptNameId(params);
		List<Long> comps = list.stream().map(SysDeptEntity::getDeptId).collect(Collectors.toList());
		List<String> compName = list.stream().map(SysDeptEntity::getName).collect(Collectors.toList());
		mmap.put("deptIdList",  Joiner.on(",").join(comps));
		mmap.put("deptName",  Joiner.on(",").join(compName));
		return urlPrefix + "/edit";
	}

	/**
	 * 选择公司树
	 */
	@GetMapping("/selectCompTree/{compId}")
	public String selectCompTree(@PathVariable("compId") Long compId, ModelMap mmap){
		mmap.put("comp", sysCompService.getById(compId));
		return urlPrefix + "/tree";
	}

	/**
	 * 校验公司名称
	 */
	@RequestMapping(value = "/checkCompNameUnique", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData checkDeptNameUnique(SysCompEntity comp){
		if(sysCompService.checkCompNameUnique(comp)){
			return success();
		}else {
			return error("已经存在!");
		}
	}


	/**
	 * 新增公司
	 */
	@GetMapping("/add/{compId}")
	public String add(@PathVariable("compId") Long compId, ModelMap mmap){
		mmap.put("comp",  sysCompService.findCompById(compId));
		return urlPrefix + "/add";
	}


	/**
	 * 加载部门列表树
	 */
	@GetMapping("/treeData")
	@ResponseBody
	public List<Ztree> treeData(){
		List<Ztree> ztrees = sysCompService.findCompTree(UserUtils.getUserInfo().getCompId(),UserUtils.getUserId());
		return ztrees;
	}

	@RequestMapping("/list")
	@RequiresPermissions("sys:comp:list")
	@ResponseBody
	public List<SysCompEntity> list(@RequestParam Map<String, Object> params) {
		return  sysCompService.findList(params);
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:comp:select")
	@ResponseBody
	public ResponseData select() {
		List<SysCompEntity> compList = sysCompService.findList(new HashMap<String, Object>());
		return success().put("compList", compList);
	}

	/**
	 * 
	 * @Description:上级公司ID(总公司为0)
	 * @author zhouzhou 18774995071@163.com
	 * @time 2018-12-05 12:26
	 * @return
	 *
	 */
	@RequestMapping("/info")
	@RequiresPermissions("sys:comp:list")
	@ResponseBody
	public ResponseData info() {
		long deptId = 0;
		if (UserUtils.getUserId() != Constant.SUPER_ADMIN) {
			List<SysCompEntity> comptList = sysCompService.findList(new HashMap<String, Object>());
			Long parentId = null;
			for (SysCompEntity compEntity : comptList) {
				if (parentId == null) {
					parentId = compEntity.getParentId();
					continue;
				}

				if (parentId > compEntity.getParentId().longValue()) {
					parentId = compEntity.getParentId();
				}
			}
			deptId = parentId;
		}
		return success().put("compId", deptId);
	}

	/**
	 * 根据公司ID获取信息
	 */
	@RequestMapping("/info/{compId}")
	@RequiresPermissions("sys:comp:info")
	@ResponseBody
	public ResponseData info(@PathVariable("compId") Long compId) {
		SysCompEntity compEntity = sysCompService.getById(compId);
		List<Long> list = sysCompDeptService.findDeptIdList(new Long[] { compId });
		compEntity.setDeptIdList(list);
		return success().put("comp", compEntity);
	}

	/**
	 * 保存配置
	 */
	@BussinessLog(title = "公司管理", businessType = BusinessType.INSERT)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresPermissions("sys:comp:add")
	@ResponseBody
	public ResponseData save(@Validated SysCompEntity comp) {
		ValidatorUtil.validateEntity(comp);
		if (!sysCompService.checkCompNameUnique(comp)){
			return error("新增公司'" + comp.getName() + "'失败,名称已存在");
		}
		sysCompService.add(comp);
		return success();
	}

	/**
	 * 修改公司
	 */
	@BussinessLog(title = "公司管理", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequiresPermissions("sys:comp:edit")
	@ResponseBody
	public ResponseData update(@Validated SysCompEntity comp) {

		ValidatorUtil.validateEntity(comp);
		if (!sysCompService.checkCompNameUnique(comp)) {
			return error("修改公司'" + comp.getName() + "'失败,名称已存在");
		}
		if(sysCompService.update(comp)){
			return success();
		}else{
			return error("修改失败!");
		}
	}

	/**
	 * 删除公司
	 */
	@BussinessLog(title = "公司管理", businessType = BusinessType.DELETE)
	@RequestMapping("/del/{compId}")
	@RequiresPermissions("sys:comp:del")
	@ResponseBody
	public ResponseData delete(@PathVariable("compId") Long compId) {

		if(UserUtils.getUserId().equals(Constant.SUPER_ADMIN) ||
				UserUtils.hasRole(Constant.SU_ADMIN)){
			// 先判断是否有子公司
			List<SysCompEntity> list = sysCompService.listByMap(new MapUtil().put("parent_id", compId));
			if (ToolUtil.isNotEmpty(list) && list.size() > 0) {
				return error("请先删除子部门");
			}
			// 在判断公司是否有分配到用户上面如果改公司已经分配到用户上,先删除用户在删
			List<SysUserEntity> users = sysUserService.listByMap(new MapUtil().put("comp_id", compId));
			if (ToolUtil.isNotEmpty(users) && users.size() > 0) {
				return error("请先删除关联用户");
			}

			// 删除
			//sysCompDeptService.deleteBatch(new Long[] { compId });
			sysCompDeptService.removeByMap(new MapUtil().put("comp_id",compId));

			if(sysCompService.removeById(compId)){
				return success();
			}else{
				return error("删除失败!");
			}
		}else{
			return error(ToolUtil.message("sys.msg.permissions"));
		}
	}
}
