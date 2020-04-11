package com.j2eefast.framework.sys.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.annotation.DataFilter;
import com.j2eefast.framework.sys.entity.SysRoleEntity;
import com.j2eefast.framework.sys.entity.SysRoleModuleEntity;
import com.j2eefast.framework.sys.mapper.SysRoleMapper;
import com.j2eefast.framework.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 角色
 * @author zhouzhou
 */
@Service
public class SysRoleService  extends ServiceImpl<SysRoleMapper, SysRoleEntity> {

	@Resource
	private SysRoleMapper sysRoleMapper;
	@Resource
	private SysRoleMenuService sysRoleMenuService;
	@Resource
	private SysRoleModuleService sysRoleModuleService;
	/**
	 * 页面展示查询翻页
	 */
	@DataFilter(subDept = true, user = false)
	public PageUtil findPage(Map<String, Object> params) {
		String roleName = (String) params.get("roleName");
		String roleKey = (String) params.get("roleKey");
		Page<SysRoleEntity> page = this.sysRoleMapper.findPage(	new Query<SysRoleEntity>(params).getPage(),
																roleName,
																roleKey,
																(String) params.get(Constant.SQL_FILTER));
		return new PageUtil(page);
	}

	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	public boolean add(SysRoleEntity role){
		if(this.save(role)){
			// 保存角色与菜单关系
			sysRoleMenuService.saveOrUpdate(role.getRoleId(), Arrays.asList(role.getMenuIds()));

			//插入角色管理 模块
			String[] ls = role.getModuleCodes().split(",");
			for(String s: ls){
				SysRoleModuleEntity sysRoleModuleEntity = new SysRoleModuleEntity();
				sysRoleModuleEntity.setModuleCode(s);
				sysRoleModuleEntity.setRoleId(role.getRoleId());
				sysRoleModuleService.save(sysRoleModuleEntity);
			}
			return true;
		}
		return false;
	}

	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public boolean update(SysRoleEntity role) {

		if(this.updateById(role)){
			// 更新角色与菜单关系
			sysRoleMenuService.saveOrUpdate(role.getRoleId(), Arrays.asList(role.getMenuIds()));

			sysRoleModuleService.deleRoleModule(role.getRoleId());

			//插入角色管理 模块
			String[] ls = role.getModuleCodes().split(",");
			for(String s: ls){
				SysRoleModuleEntity sysRoleModuleEntity = new SysRoleModuleEntity();
				sysRoleModuleEntity.setModuleCode(s);
				sysRoleModuleEntity.setRoleId(role.getRoleId());
				sysRoleModuleService.save(sysRoleModuleEntity);
			}
			return true;
		}
		return false;
	}

	public boolean deleteBatchByIds(Long[] ids) {

		List<SysRoleEntity> list = sysRoleMapper.findRoleByIds(ids);

		if(list != null && list.size() > 0){
			throw new RxcException(String.format("%1$s已分配,不能删除", list.get(0).getRoleName()));
		}

		// 删除角色与菜单关联
		sysRoleMenuService.deleteBatchByRoleIds(ids);

		// 删除角色
		return  this.removeByIds(Arrays.asList(ids));
	}


	public boolean checkRoleNameUnique(SysRoleEntity role) {
		Long roleId = ToolUtil.isEmpty(role.getRoleId())?-1L:role.getRoleId();
		SysRoleEntity info = sysRoleMapper.checkRoleNameUnique(role.getRoleName());
		if(!ToolUtil.isEmpty(info) && !info.getRoleId().equals(roleId)){
			return  false;
		}
		return true;
	}

	public boolean checkRoleKeyUnique(SysRoleEntity role) {
		Long roleId = ToolUtil.isEmpty(role.getRoleId())?-1L:role.getRoleId();
		SysRoleEntity info = sysRoleMapper.checkRoleKeyUnique(role.getRoleKey());
		if(!ToolUtil.isEmpty(info) && !info.getRoleId().equals(roleId)){
			return  false;
		}
		return true;
	}

	public List<SysRoleEntity> selectRolesByUserId(Long userId) {
		return sysRoleMapper.getRolesByUserId(userId);
	}

	public boolean changeStatus(SysRoleEntity role) {
		return this.updateById(role);
	}


	public List<SysRoleEntity> findByRolesByUserId(Long userId){
		List<SysRoleEntity> userRole =  sysRoleMapper.getRolesByUserId(userId);
		List<SysRoleEntity> roles = this.list(new QueryWrapper<SysRoleEntity>().eq("status","0"));
		for (SysRoleEntity r : roles) {
			for (SysRoleEntity userR : userRole) {
				if (r.getRoleId().longValue() == userR.getRoleId().longValue()){
					r.setFlag(true);
					break;
				}
			}
		}
		return roles;
	}

}
