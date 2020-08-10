package com.j2eefast.framework.sys.service;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.utils.MapUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysRoleDeptEntity;
import com.j2eefast.framework.sys.mapper.SysRoleDeptMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 角色与部门对应关系
 * @author zhouzhou
 */
@Service
public class SysRoleDeptService  extends ServiceImpl<SysRoleDeptMapper,SysRoleDeptEntity> {

	@Resource
	private SysRoleDeptMapper sysRoleDeptMapper;

	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
		// 先删除角色与部门关系
		this.removeByMap(new MapUtil().put("role_id",roleId));
		if (ToolUtil.isEmpty(deptIdList)) {
			return;
		}

		// 保存角色与菜单关系
//		List<SysRoleDeptEntity> list = new ArrayList<>(deptIdList.size());
		for (Long deptId : deptIdList) {
			SysRoleDeptEntity sysRoleDeptEntity = new SysRoleDeptEntity();
			sysRoleDeptEntity.setDeptId(deptId);
			sysRoleDeptEntity.setRoleId(roleId);
			this.save(sysRoleDeptEntity);
//			list.add(sysRoleDeptEntity);
		}
//		this.saveBatch(list);
	}


	public List<Long> findDeptIdList(Long[] roleIds) {
		return sysRoleDeptMapper.findDeptIdList(roleIds);
	}

	public boolean deleteBatchByRoleIds(Long[] roleIds) {
		return this.remove(new QueryWrapper<SysRoleDeptEntity>().in("role_id",roleIds));
	}

}
