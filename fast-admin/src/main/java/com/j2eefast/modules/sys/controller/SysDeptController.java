package com.j2eefast.modules.sys.controller;

import java.util.*;

import com.j2eefast.common.core.base.entity.Ztree;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.framework.sys.service.SysCompService;
import com.j2eefast.framework.sys.service.SysUserDeptService;
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
import com.j2eefast.framework.sys.service.SysCompDeptService;
import com.j2eefast.framework.sys.service.SysDeptService;


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
	private SysCompDeptService sysCompDeptService;

	@Autowired
	private SysCompService sysCompService;
	
	@Autowired
	private SysUserDeptService sysUserDeptService;


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
	 * 新增地区或者线路
	 */
	@GetMapping("/add/{deptId}")
	public String add(@PathVariable("deptId") Long deptId, ModelMap mmap){
		mmap.put("dept",  sysDeptService.findDeptById(deptId));
		return urlPrefix + "/add";
	}


	/**
	 * 修改
	 */
	@GetMapping("/edit/{deptId}")
	public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap){
		SysDeptEntity dept = sysDeptService.findDeptById(deptId);
		mmap.put("dept", dept);
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
	public List<SysDeptEntity> list(@RequestParam Map<String, Object> params) {
		List<SysDeptEntity> deptList = sysDeptService.findDeptList(params);
		return deptList;
	}

	/**
	 * 加载部门列表树
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
			root.setDeptId(0L);
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
	@RequestMapping(value = "/checkDeptNameUnique", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData checkDeptNameUnique(SysDeptEntity dept){
		 if(sysDeptService.checkDeptNameUnique(dept)){
			 return success();
		 }else {
			 return success();
		 }
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select/{compId}")
	@RequiresPermissions("sys:dept:select")
	@ResponseBody
	public ResponseData select(@PathVariable("compId") Long compId) {

		if(compId == 1L){ //总公司
			List<SysDeptEntity> deptList = sysDeptService.findPage(new HashMap<>());
			return success().put("deptList", deptList);
		}else{
			//获取公司下所有子公司
			List<Long> compIds = sysCompService.getSubDeptIdList(compId);
			compIds.add(compId);
			// 获取公司关联地区ID
			List<Long> deptIds = sysCompDeptService.findDeptIdList(compIds.stream().toArray(Long[]::new));
			List<SysDeptEntity> deptList = sysDeptService.list(new QueryWrapper<SysDeptEntity>().in("dept_id", deptIds));
			return success().put("deptList", deptList);
		}
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
	@BussinessLog(title = "地区管理", businessType = BusinessType.INSERT)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresPermissions("sys:dept:add")
	@ResponseBody
	public ResponseData save(@Validated SysDeptEntity dept) {

		if (!sysDeptService.checkDeptNameUnique(dept))
		{
			return error("新增地区'" + dept.getName() + "'失败,名称已存在");
		}
		sysDeptService.save(dept);
		return success();
	}


	/**
	 * 修改
	 */
	@BussinessLog(title = "地区管理", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequiresPermissions("sys:dept:edit")
	@ResponseBody
	public ResponseData update(@Validated SysDeptEntity dept) {
		return sysDeptService.updateById(dept)?success(): error("修改失败!");
	}

	/**
	 * 删除
	 */
	@BussinessLog(title = "地区管理", businessType = BusinessType.DELETE)
	@RequestMapping("/del/{deptId}")
	@RequiresPermissions("sys:dept:del")
	@ResponseBody
	public ResponseData delete(@PathVariable("deptId") Long deptId) {
		// 判断是否有子部门
		List<Long> deptList = sysDeptService.findDetpIdList(deptId);
		if (ToolUtil.isNotEmpty(deptList) && deptList.size() > 0) {
			return error("存在下级地区,不允许删除");
		}
		List<Long> userList = sysUserDeptService.findUserIdList(deptId);
		if(ToolUtil.isNotEmpty(userList) && userList.size() > 0) {
			return error("地区存在用户,不允许删除");
		}
		return sysDeptService.removeById(deptId)? success(): error("删除失败!");
	}

}
