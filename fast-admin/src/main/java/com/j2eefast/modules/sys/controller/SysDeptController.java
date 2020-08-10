package com.j2eefast.modules.sys.controller;

import java.util.*;

import com.j2eefast.common.core.base.entity.Ztree;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.utils.MapUtil;
import com.j2eefast.common.core.utils.ValidatorUtil;
import com.j2eefast.framework.sys.entity.SysCompEntity;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import com.j2eefast.framework.sys.service.*;
import com.j2eefast.framework.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.framework.utils.Constant;
import com.j2eefast.framework.sys.entity.SysDeptEntity;


/**
 * 地区管理
 * @author zhouzhou
 * @date 2020-03-07 14:50
 */
@Controller
@RequestMapping("/sys/dept")
public class SysDeptController extends BaseController {

	private String urlPrefix = "modules/sys/dept";

	@Autowired
	private SysDeptService sysDeptService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysCompDeptService sysCompDeptService;

	@Autowired
	private SysCompService sysCompService;
	
	@Autowired
	private SysUserDeptService sysUserDeptService;

	@Autowired
	private SysAreaService sysAreaService;

	/**
	 * 选择公司树
	 */
	@GetMapping("/selectDeptTree")
	public String selectDeptTree(){
		return urlPrefix + "/tree";
	}

	/**
	 * 选择添加地区或者修改地区回显树
	 */
	@GetMapping("/deptTree/{deptId}")
	public String deptTree(@PathVariable("deptId") Long deptId, ModelMap mmap){
		mmap.put("dept", sysDeptService.findDeptById(deptId));
		return urlPrefix + "/deptTree";
	}

	/**
	 * 新增机构跳转页面
	 */
	@GetMapping("/add/{deptId}")
	public String add(@PathVariable("deptId") Long deptId, ModelMap mmap){
		mmap.put("dept",  sysCompService.findCompById(deptId));
		return urlPrefix + "/add";
	}


	/**
	 * 修改
	 */
	@GetMapping("/edit/{deptId}")
	public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap){
		SysCompEntity sysCompEntity = sysCompService.findCompById(deptId);
		mmap.put("dept",  sysCompEntity);
		return urlPrefix + "/edit";
	}



	/*
	 地区主页
	* */
	@RequiresPermissions("sys:dept:view")
	@GetMapping()
	public String dept() {
		return urlPrefix + "/dept";
	}

	/**
	 * 主要列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:dept:list")
	@ResponseBody
	public List<SysCompEntity> list(@RequestParam Map<String, Object> params) {
		List<SysCompEntity> compList = sysCompService.getDeptList(params);
		return compList;
	}



	/**
	 * 加载部门列表树// 0:表示地区，1：线路
	 */
	@GetMapping("/treeData/{type}")
	@ResponseBody
	public List<Ztree> treeData(@PathVariable("type") Long type){
		List<Ztree> ztrees = sysDeptService.findDeptTree(type);
		return ztrees;
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:dept:select")
	@ResponseBody
	public ResponseData select() {
		List<SysDeptEntity> deptList = sysDeptService.findPage(new HashMap<String, Object>(1));
		// 添加一级部门
		if (UserUtils.getUserId().equals(Constant.SUPER_ADMIN)) {
			SysDeptEntity root = new SysDeptEntity();
			root.setId(0L);
			root.setName("一级地区");
			root.setParentId(-1L);
			root.setOpen(true);
			deptList.add(root);
		}

		return success().put("deptList", deptList);
	}



	/**
	 * 校验部门名称
	 */
	@RequestMapping(value = "/checkCompNameUnique", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData checkCompNameUnique(SysCompEntity dept){
		 if(sysCompService.checkCompNameUnique(dept)){
			 return success();
		 }else {
			 return error("已经存在!");
		 }
	}

	/**
	 * 选择部门 -- >> 通过组件联动获取指定公司下所有信息
	 */
	@RequestMapping("/selectZtree")
	@RequiresPermissions("sys:dept:select")
	@ResponseBody
	public ResponseData selectZtree() {
		Long compId = Long.parseLong(super.getPara("correlationId","-1"));
		List<Ztree> ztrees = sysCompService.getCompIdToLeveAll(compId);
		return success().put("deptList", ztrees);
	}

	/**
	 * 上级部门Id(管理员则为0)
	 */
	@RequestMapping("/info")
	@RequiresPermissions("sys:dept:list")
	@ResponseBody
	public ResponseData info() {
		long deptId = 0;
		if (!UserUtils.getUserId().equals(Constant.SUPER_ADMIN)) {
			List<SysDeptEntity> deptList = sysDeptService.findPage(new HashMap<String, Object>());
			Long parentId = null;
			for (SysDeptEntity sysDeptEntity : deptList) {
				if (parentId == null) {
					parentId = sysDeptEntity.getParentId();
					continue;
				}

				if (parentId > sysDeptEntity.getParentId().longValue()) {
					parentId = sysDeptEntity.getParentId();
				}
			}
			deptId = parentId;
		}

		return success().put("deptId", deptId);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{deptId}")
	@RequiresPermissions("sys:dept:info")
	@ResponseBody
	public ResponseData info(@PathVariable("deptId") Long deptId) {
		SysDeptEntity dept = sysDeptService.getById(deptId);
		return success().put("dept", dept);
	}

	/**
	 * 保存
	 */
	@BussinessLog(title = "机构管理", businessType = BusinessType.INSERT)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresPermissions("sys:dept:add")
	@ResponseBody
	public ResponseData save(@Validated SysCompEntity dept) {

		if (!sysCompService.checkCompNameUnique(dept)){
			return error("新增地区'" + dept.getName() + "'失败,名称已存在");
		}
		return sysCompService.save(dept)?success():error("保存失败");
	}


	/**
	 * 修改
	 */
	@BussinessLog(title = "地区管理", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequiresPermissions("sys:dept:edit")
	@ResponseBody
	public ResponseData update(@Validated SysCompEntity dept) {
		ValidatorUtil.validateEntity(dept);
		if (!sysCompService.checkCompNameUnique(dept)) {
			return error("修改公司'" + dept.getName() + "'失败,名称已存在");
		}
		if(sysCompService.update(dept)){
			return success();
		}else{
			return error("修改失败!");
		}
	}

	/**
	 * 加载角色部门（数据权限）列表树
	 */
	@GetMapping("/roleDeptTreeData/{roleId}")
	@ResponseBody
	public List<Ztree> deptTreeData(@PathVariable("roleId") Long roleId) {
		List<Ztree> ztrees = sysCompService.roleDeptTreeData(roleId);
		return ztrees;
	}


	/**
	 * 删除
	 */
	@BussinessLog(title = "地区管理", businessType = BusinessType.DELETE)
	@RequestMapping("/del/{deptId}")
	@RequiresPermissions("sys:dept:del")
	@ResponseBody
	public ResponseData delete(@PathVariable("deptId") Long deptId) {
//		// 判断是否有子部门
//		List<Long> deptList = sysDeptService.findDetpIdList(deptId);
//		if (ToolUtil.isNotEmpty(deptList) && deptList.size() > 0) {
//			return error("存在下级地区,不允许删除");
//		}
//		List<Long> userList = sysUserDeptService.findUserIdList(deptId);
//		if(ToolUtil.isNotEmpty(userList) && userList.size() > 0) {
//			return error("地区存在用户,不允许删除");
//		}
//		return sysDeptService.removeById(deptId)? success(): error("删除失败!");
		if(UserUtils.getUserId().equals(Constant.SUPER_ADMIN) ||
				UserUtils.hasRole(Constant.SU_ADMIN)){
			// 先判断是否有子公司
			List<SysCompEntity> list = sysCompService.listByMap(new MapUtil().put("parent_id", deptId));
			if (ToolUtil.isNotEmpty(list) && list.size() > 0) {
				return error("请先删除子部门");
			}
			// 在判断公司是否有分配到用户上面如果改公司已经分配到用户上,先删除用户在删
			List<SysUserEntity> users = sysUserService.listByMap(new MapUtil().put("comp_id", deptId));
			if (ToolUtil.isNotEmpty(users) && users.size() > 0) {
				return error("请先删除关联用户");
			}

			// 删除
			//sysCompDeptService.deleteBatch(new Long[] { compId });
			//sysCompDeptService.removeByMap(new MapUtil().put("comp_id",compId));

			if(sysCompService.removeById(deptId)){
				return success();
			}else{
				return error("删除失败!");
			}
		}else{
			return error(ToolUtil.message("sys.msg.permissions"));
		}
	}

}
