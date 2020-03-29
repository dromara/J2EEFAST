package com.fast.framework.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.utils.MapUtil;
import com.fast.framework.sys.service.SysUserDeptService;
import com.fast.framework.sys.dao.SysUserDeptDao;
import com.fast.framework.sys.entity.SysUserDeptEntity;

/**
 * 
 * @Description:用户与公司地区关系
 * @author ZhouHuan 18774995071@163.com
 * @time 2018-12-05 23:01
的
 *
 */
@Service("sysUserDeptService")
public class SysUserDeptServiceImpl extends ServiceImpl<SysUserDeptDao, SysUserDeptEntity>
		implements SysUserDeptService {

	@Override
	public void saveOrUpdate(Long userId, List<Long> deptIdList) {
		// 先删除用户与公司地区关系
		this.removeByMap(new MapUtil().put("user_id", userId)); // 使用mybatisplus 方法删除

		if (deptIdList == null || deptIdList.size() == 0) { // 没有对应地区
			return;
		}

		// 保存关系
		List<SysUserDeptEntity> list = new ArrayList<>(deptIdList.size());
		for (Long deptId : deptIdList) {
			SysUserDeptEntity sysUserDeptEntity = new SysUserDeptEntity();
			sysUserDeptEntity.setUserId(userId);
			sysUserDeptEntity.setDeptId(deptId);
			list.add(sysUserDeptEntity);
		}

		this.saveBatch(list);
	}

	@Override
	public List<Long> queryDeptIdList(Long userId) {
		return baseMapper.queryDeptIdList(userId);
	}

	@Override
	public List<SysUserDeptEntity> queryListByUserId(Long userId) {
		return this.baseMapper.queryListByUserId(userId);
	}

	@Override
	public int deleteBatch(Long[] deptIds) {
		return baseMapper.deleteBatch(deptIds);
	}

	@Override
	public int deleteByUserIdBatch(Long[] userIds) {
		return baseMapper.deleteByUserIdBatch(userIds);
	}

	@Override
	public List<Long> queryUserIdList(Long deptId) {
		List<Long> retList = new ArrayList<Long>();
		List<Object> list = this.listObjs(new QueryWrapper<SysUserDeptEntity>().eq("dept_id", deptId).select("user_id"));
		for(Object l : list) {
			retList.add((long)l);
		}
		return retList;
	}

}
