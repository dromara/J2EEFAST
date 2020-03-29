package com.fast.framework.sys.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fast.common.core.exception.RxcException;
import com.fast.common.core.page.Query;
import com.fast.framework.sys.entity.SysRoleModuleEntity;
import com.fast.framework.sys.entity.SysUserRoleEntity;
import com.fast.framework.sys.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.annotation.DataFilter;
import com.fast.framework.utils.Constant;

import com.fast.framework.sys.dao.SysRoleDao;
import com.fast.framework.sys.entity.SysRoleEntity;

/**
 * 角色
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private SysRoleModuleService sysRoleModuleService;

	@Override
	@DataFilter(subDept = true, user = false)
	public PageUtil queryPage(Map<String, Object> params) {
		String roleName = (String) params.get("roleName");
		String roleKey = (String) params.get("roleKey");
		Page<SysRoleEntity> page = this.baseMapper.selectPage(new Query<SysRoleEntity>(params).getPage(),
				new QueryWrapper<SysRoleEntity>().like(ToolUtil.isNotEmpty(roleName), "role_name", roleName)
						.like(ToolUtil.isNotEmpty(roleKey), "role_key", roleKey)
						.eq("del_flag","0")
						.apply(params.get(Constant.SQL_FILTER) != null,
								(String) params.get(Constant.SQL_FILTER)));

		// 屏蔽部门
//		for (SysRoleEntity sysRoleEntity : page.getRecords()) {
//			SysDeptEntity sysDeptEntity = sysDeptService.selectById(sysRoleEntity.getDeptId());
//			if (sysDeptEntity != null) {
//				sysRoleEntity.setDeptName(sysDeptEntity.getName());
//			}
//		}

		return new PageUtil(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insert(SysRoleEntity role) {

		role.setCreateTime(new Date());

		this.save(role);

		// 保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		//插入角色管理 模块
		String[] ls = role.getModuleCodes().split(",");
		for(String s: ls){
			SysRoleModuleEntity sysRoleModuleEntity = new SysRoleModuleEntity();
			sysRoleModuleEntity.setModuleCode(s);
			sysRoleModuleEntity.setRoleId(role.getRoleId());
			sysRoleModuleService.save(sysRoleModuleEntity);
		}
		// 屏蔽
		// 保存角色与部门关系
		// sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysRoleEntity role) {


		//this.updateAllColumnById(role);

		// 更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		sysRoleModuleService.deleRoleModule(role.getRoleId());

		//插入角色管理 模块
		String[] ls = role.getModuleCodes().split(",");
		for(String s: ls){
			SysRoleModuleEntity sysRoleModuleEntity = new SysRoleModuleEntity();
			sysRoleModuleEntity.setModuleCode(s);
			sysRoleModuleEntity.setRoleId(role.getRoleId());
			sysRoleModuleService.save(sysRoleModuleEntity);
		}

//		// 保存角色与部门关系 暂时不用
//		sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());

		this.baseMapper.updateRole(role);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] roleIds) {



		// 删除角色与部门关联
		// sysRoleDeptService.deleteBatch(roleIds);

		// 删除角色与用户关联 不能删除
		//sysUserRoleService.deleteBatch(roleIds);
		List<SysRoleEntity> list = this.baseMapper.selectRoleById(roleIds);

		if(list != null && list.size() > 0){
			throw new RxcException(String.format("%1$s已分配,不能删除", list.get(0).getRoleName()));
		}

		// 删除角色与菜单关联
		sysRoleMenuService.deleteBatch(roleIds);

		// 删除角色
		this.removeByIds(Arrays.asList(roleIds));
	}

	@Override
	public boolean checkRoleNameUnique(SysRoleEntity role) {
		Long roleId = ToolUtil.isEmpty(role.getRoleId())?-1L:role.getRoleId();
		SysRoleEntity info = this.baseMapper.checkRoleNameUnique(role.getRoleName());
		if(!ToolUtil.isEmpty(info) && info.getRoleId() != roleId){
			return  false;
		}
		return true;
	}

	@Override
	public boolean checkRoleKeyUnique(SysRoleEntity role) {
		Long roleId = ToolUtil.isEmpty(role.getRoleId())?-1L:role.getRoleId();
		SysRoleEntity info = this.baseMapper.checkRoleKeyUnique(role.getRoleKey());
		if(!ToolUtil.isEmpty(info) && info.getRoleId() != roleId){
			return  false;
		}
		return true;
	}

	@Override
	public List<SysRoleEntity> selectRolesByUserId(Long userId) {
		return this.baseMapper.selectRolesByUserId(userId);
	}

	@Override
	public int changeStatus(SysRoleEntity role) {
		return this.baseMapper.updateRole(role);
	}

//	@Override
//	public List<SysRoleEntity> selectRoleById(Long[] roleId) {
//		return this.selectList(new QueryWrapper<SysRoleEntity>().in("role_id",roleId));
//	}

}
