package com.fast.modules.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.fast.common.core.base.entity.Ztree;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.common.core.enums.BusinessType;
import com.fast.framework.sys.entity.SysDeptEntity;
import com.fast.framework.sys.service.SysDeptService;
import com.fast.framework.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.fast.common.core.utils.MapUtil;
import com.fast.common.core.utils.R;
import com.fast.common.core.utils.ToolUtil;
import com.fast.common.core.utils.ValidatorUtil;
import com.fast.framework.sys.entity.SysCompEntity;
import com.fast.framework.sys.entity.SysUserEntity;
import com.fast.framework.sys.service.SysCompDeptService;
import com.fast.framework.sys.service.SysCompService;
import com.fast.framework.sys.service.SysUserService;
import com.fast.framework.utils.AbstractController;
import com.fast.framework.utils.Constant;

/**
 * 公司管理控制器
 * @author zhouzhou
 * @date 2018-12-05 09:15
 */
@Controller
@RequestMapping("/sys/comp")
public class SysCompController extends AbstractController {
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
		mmap.put("comp",  sysCompService.selectCompById(compId));
		Map<String, Object> params = new HashMap<>(1);
		params.put("compId",compId);
		List<SysDeptEntity> list = sysDeptService.selectByDeptNameId(params);
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
	public R checkDeptNameUnique(SysCompEntity comp){
		if(sysCompService.checkCompNameUnique(comp)){
			return R.ok();
		}else {
			return R.error();
		}
	}


	/**
	 * 新增公司
	 */
	@GetMapping("/add/{compId}")
	public String add(@PathVariable("compId") Long compId, ModelMap mmap){
		mmap.put("comp",  sysCompService.selectCompById(compId));
		return urlPrefix + "/add";
	}


	/**
	 * 加载部门列表树
	 */
	@GetMapping("/treeData")
	@ResponseBody
	public List<Ztree> treeData(){
		List<Ztree> ztrees = sysCompService.selectCompTree(getUser().getCompId(),getUserId());
		return ztrees;
	}

	@RequestMapping("/list")
	@RequiresPermissions("sys:comp:list")
	@ResponseBody
	public List<SysCompEntity> list(@RequestParam Map<String, Object> params) {
		return  sysCompService.queryList(params);
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:comp:select")
	@ResponseBody
	public R select() {
		List<SysCompEntity> compList = sysCompService.queryList(new HashMap<String, Object>());
//		// 添加一级部门
//		if (getUserId() == Constant.SUPER_ADMIN) {
//			SysCompEntity root = new SysCompEntity();
//			root.setCompId(0L);
//			root.setName("总公司");
//			root.setParentId(-1L);
//			root.setOpen(true);
//			compList.add(root);
//		}
		return R.ok().put("compList", compList);
	}

	/**
	 * 
	 * @Description:上级公司ID(总公司为0)
	 * @author ZhouHuan 18774995071@163.com
	 * @time 2018-12-05 12:26
	 * @return
	 *
	 */
	@RequestMapping("/info")
	@RequiresPermissions("sys:comp:list")
	@ResponseBody
	public R info() {
		long deptId = 0;
		if (getUserId() != Constant.SUPER_ADMIN) {
			List<SysCompEntity> comptList = sysCompService.queryList(new HashMap<String, Object>());
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
		return R.ok().put("compId", deptId);
	}

	/**
	 * 根据公司ID获取信息
	 */
	@RequestMapping("/info/{compId}")
	@RequiresPermissions("sys:comp:info")
	@ResponseBody
	public R info(@PathVariable("compId") Long compId) {
		SysCompEntity compEntity = sysCompService.getById(compId);
		List<Long> list = sysCompDeptService.queryDeptIdList(new Long[] { compId });
		compEntity.setDeptIdList(list);
		return R.ok().put("comp", compEntity);
	}

	/**
	 * 保存配置
	 */
	@BussinessLog(title = "公司管理", businessType = BusinessType.INSERT)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresPermissions("sys:comp:add")
	@ResponseBody
	public R save(@Validated SysCompEntity comp) {
		ValidatorUtil.validateEntity(comp);
		if (!sysCompService.checkCompNameUnique(comp))
		{
			return R.error("新增公司'" + comp.getName() + "'失败,名称已存在");
		}
		comp.setCreateBy(getUser().getUsername());
		sysCompService.save(comp);
		return R.ok();
	}

	/**
	 * 修改公司
	 */
	@BussinessLog(title = "公司管理", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequiresPermissions("sys:comp:edit")
	@ResponseBody
	public R update(@Validated SysCompEntity comp) {

		ValidatorUtil.validateEntity(comp);
		if (!sysCompService.checkCompNameUnique(comp))
		{
			return R.error("修改公司'" + comp.getName() + "'失败,名称已存在");
		}
		comp.setUpdateBy(getUser().getUsername());
		if(sysCompService.updateComp(comp)){
			return R.ok();
		}else{
			return R.error();
		}
	}

	/**
	 * 删除公司
	 */
	@BussinessLog(title = "公司管理", businessType = BusinessType.DELETE)
	@RequestMapping("/del/{compId}")
	@RequiresPermissions("sys:comp:del")
	@ResponseBody
	public R delete(@PathVariable("compId") Long compId) {

		if(getUserId().equals(Constant.SUPER_ADMIN) ||
				ShiroUtils.isPermissions(Constant.SU_ADMIN)){
			// 先判断是否有子公司
			List<SysCompEntity> list = sysCompService.listByMap(new MapUtil().put("parent_id", compId));
			if (ToolUtil.isNotEmpty(list) && list.size() > 0) {
				return R.error("请先删除子部门");
			}
			// 在判断公司是否有分配到用户上面如果改公司已经分配到用户上,先删除用户在删
			List<SysUserEntity> users = sysUserService.listByMap(new MapUtil().put("comp_id", compId));
			if (ToolUtil.isNotEmpty(users) && users.size() > 0) {
				return R.error("请先删除关联用户");
			}

			// 删除
			sysCompDeptService.deleteBatch(new Long[] { compId });

			if(sysCompService.removeById(compId)){
				return R.ok();
			}else{
				return R.error();
			}
		}else{
			return R.error(ToolUtil.message("sys.msg.permissions"));
		}
	}
}
