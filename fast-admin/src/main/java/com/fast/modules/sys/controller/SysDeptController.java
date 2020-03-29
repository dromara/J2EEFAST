package com.fast.modules.sys.controller;

import java.util.*;

import com.fast.common.core.base.entity.Ztree;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.common.core.enums.BusinessType;
import com.fast.framework.sys.service.SysCompService;
import com.fast.framework.sys.service.SysUserDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fast.common.core.utils.R;
import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.utils.AbstractController;
import com.fast.framework.utils.Constant;
import com.fast.framework.sys.entity.SysDeptEntity;
import com.fast.framework.sys.service.SysCompDeptService;
import com.fast.framework.sys.service.SysDeptService;


/**
 * 地区管理
 * @author zhouzhou
 * @date 2020-03-07 14:50
 */
@Controller
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {

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
		mmap.put("dept", sysDeptService.selectDeptById(deptId));
		return urlPrefix + "/deptTree";
	}

	/**
	 * 新增地区或者线路
	 */
	@GetMapping("/add/{deptId}")
	public String add(@PathVariable("deptId") Long deptId, ModelMap mmap){
		mmap.put("dept",  sysDeptService.selectDeptById(deptId));
		return urlPrefix + "/add";
	}


	/**
	 * 修改
	 */
	@GetMapping("/edit/{deptId}")
	public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap){
		SysDeptEntity dept = sysDeptService.selectDeptById(deptId);
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
		List<SysDeptEntity> deptList = sysDeptService.selectDeptList(params);
		return deptList;
	}

	/**
	 * 加载部门列表树
	 */
	@GetMapping("/treeData/{type}")
	@ResponseBody
	public List<Ztree> treeData(@PathVariable("type") Long type)
	{
		List<Ztree> ztrees = sysDeptService.selectDeptTree(type);
		return ztrees;
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:dept:select")
	@ResponseBody
	public R select() {
		List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>(1));

		// 添加一级部门
		if (getUserId() == Constant.SUPER_ADMIN) {
			SysDeptEntity root = new SysDeptEntity();
			root.setDeptId(0L);
			root.setName("一级地区");
			root.setParentId(-1L);
			root.setOpen(true);
			deptList.add(root);
		}

		return R.ok().put("deptList", deptList);
	}



	/**
	 * 校验部门名称
	 */
	@RequestMapping(value = "/checkDeptNameUnique", method = RequestMethod.POST)
	@ResponseBody
	public R checkDeptNameUnique(SysDeptEntity dept)
	{
		 if(sysDeptService.checkDeptNameUnique(dept)){
			 return R.ok();
		 }else {
			 return R.error();
		 }
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select/{compId}")
	@RequiresPermissions("sys:dept:select")
	@ResponseBody
	public R select(@PathVariable("compId") Long compId) {

		if(compId == 1L){ //总公司
			List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<>());
			return R.ok().put("deptList", deptList);
		}else{
			//获取公司下所有子公司
			List<Long> compIds = sysCompService.getSubDeptIdList(compId);
			compIds.add(compId);
			// 获取公司关联地区ID
			List<Long> deptIds = sysCompDeptService.queryDeptIdList(compIds.stream().toArray(Long[]::new));
			List<SysDeptEntity> deptList = sysDeptService.list(new QueryWrapper<SysDeptEntity>().in("dept_id", deptIds));
			return R.ok().put("deptList", deptList);
		}
	}

	/**
	 * 上级部门Id(管理员则为0)
	 */
	@RequestMapping("/info")
	@RequiresPermissions("sys:dept:list")
	@ResponseBody
	public R info() {
		long deptId = 0;
		if (getUserId() != Constant.SUPER_ADMIN) {
			List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
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

		return R.ok().put("deptId", deptId);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{deptId}")
	@RequiresPermissions("sys:dept:info")
	@ResponseBody
	public R info(@PathVariable("deptId") Long deptId) {
		SysDeptEntity dept = sysDeptService.getById(deptId);
		return R.ok().put("dept", dept);
	}

	/**
	 * 保存
	 */
	@BussinessLog(title = "地区管理", businessType = BusinessType.INSERT)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresPermissions("sys:dept:add")
	@ResponseBody
	public R save(@Validated SysDeptEntity dept) {

		if (!sysDeptService.checkDeptNameUnique(dept))
		{
			return R.error("新增地区'" + dept.getName() + "'失败,名称已存在");
		}
		dept.setCreateBy(getUser().getUsername());
		dept.setCreateTime(new Date());
		sysDeptService.save(dept);
		/*if(dept.getType() == 1) {
			TbcLineEntity tbcLineEntity = new TbcLineEntity();
			tbcLineEntity.setLineId(dept.getDeptId());
			tbcLineEntity.setLineName(dept.getName());
			tbcLineService.serInsert(tbcLineEntity);
		}*/
		return R.ok();
	}


//	/**
//	 * 保存
//	 */
//	@Log(title = "部门管理", businessType = BusinessType.UPDATE)
//	@RequiresPermissions("system:dept:edit")
//	@PostMapping("/edit")
//	@ResponseBody
//	public AjaxResult editSave(@Validated SysDept dept)
//	{
//		if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
//		{
//			return error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
//		}
//		else if (dept.getParentId().equals(dept.getDeptId()))
//		{
//			return error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
//		}
//		dept.setUpdateBy(ShiroUtils.getLoginName());
//		return toAjax(deptService.updateDept(dept));
//	}

	/**
	 * 修改
	 */
	@BussinessLog(title = "地区管理", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequiresPermissions("sys:dept:edit")
	@ResponseBody
	public R update(@Validated SysDeptEntity dept) {
		dept.setUpdateBy(getUser().getUsername());
		//查询地区是否关联公司
		if(sysDeptService.updateDept(dept)){
			return R.ok();
		}else{
			return R.error();
		}
	}

	/**
	 * 删除
	 */
	@BussinessLog(title = "地区管理", businessType = BusinessType.DELETE)
	@RequestMapping("/del/{deptId}")
	@RequiresPermissions("sys:dept:del")
	@ResponseBody
	public R delete(@PathVariable("deptId") Long deptId) {
		// 判断是否有子部门
		List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
		if (ToolUtil.isNotEmpty(deptList) && deptList.size() > 0) {
			return R.error("存在下级地区,不允许删除");
		}
		List<Long> userList = sysUserDeptService.queryUserIdList(deptId);
		if(ToolUtil.isNotEmpty(userList) && userList.size() > 0) {
			return R.error("地区存在用户,不允许删除");
		}
		if(sysDeptService.removeById(deptId)){
			return R.ok();
		}
		return R.error();
	}

}
