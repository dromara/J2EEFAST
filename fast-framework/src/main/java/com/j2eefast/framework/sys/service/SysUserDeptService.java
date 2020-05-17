package com.j2eefast.framework.sys.service;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.utils.MapUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysUserDeptEntity;
import com.j2eefast.framework.sys.mapper.SysUserDeptMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 
 * @Description:用户与公司地区关系
 * @author zhouzhou 18774995071@163.com
 * @time 2018-12-05 22:58
 *
 */
@Service
public class SysUserDeptService extends ServiceImpl<SysUserDeptMapper, SysUserDeptEntity> {


	@Resource
	private SysUserDeptMapper sysUserDeptMapper;

	public void saveOrUpdate(Long userId, List<Long> deptIdList) {
		// 先删除用户与公司地区关系
		this.removeByMap(new MapUtil().put("user_id", userId));

		if (ToolUtil.isEmpty(deptIdList)) {
			return;
		}
		// 保存关系
//		List<SysUserDeptEntity> list = new ArrayList<>(deptIdList.size());
		for (Long deptId : deptIdList) {
			SysUserDeptEntity sysUserDeptEntity = new SysUserDeptEntity();
			sysUserDeptEntity.setUserId(userId);
			sysUserDeptEntity.setDeptId(deptId);
//			list.add(sysUserDeptEntity);
			this.save(sysUserDeptEntity);
		}
//		this.saveBatch(list);
	}


	public List<Long> findDeptIdList(Long userId) {
		return sysUserDeptMapper.findDeptIdList(userId);
	}

	public List<Long> findUserIdList(Long deptId) {
		return sysUserDeptMapper.findUserIdList(deptId);
	}

	public List<SysUserDeptEntity> findListByUserId(Long userId) {
		return sysUserDeptMapper.findListByUserId(userId);
	}

	public boolean deleteBatchByDeptIds(Long[] deptIds) {

		return this.remove(new QueryWrapper<SysUserDeptEntity>().in("dept_id",deptIds));
	}

	public boolean deleteBatchByUserIds(Long[] userIds) {

		return this.remove(new QueryWrapper<SysUserDeptEntity>().in("user_id",userIds));
	}

}
