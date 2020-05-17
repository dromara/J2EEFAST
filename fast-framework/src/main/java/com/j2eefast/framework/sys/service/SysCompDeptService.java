package com.j2eefast.framework.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.utils.MapUtil;
import com.j2eefast.framework.sys.entity.SysCompDeptEntity;
import com.j2eefast.framework.sys.mapper.SysCompDeptMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysCompDeptService extends ServiceImpl<SysCompDeptMapper,SysCompDeptEntity> {

	@Resource
	private  SysCompDeptMapper sysCompDeptMapper;


	public void saveOrUpdate(Long compId, List<Long> deptIdList) {
		// 先删除对应关系
		this.removeByMap(new MapUtil().put("comp_id",compId));

		// 如果对于地区为0 不操作
		if (deptIdList == null || deptIdList.size() == 0) {
			return;
		}

		// 保存公司与地区对应关系
//		List<SysCompDeptEntity> list = new ArrayList<>(deptIdList.size());
		for (Long deptId : deptIdList) {
			SysCompDeptEntity compDeptEntity = new SysCompDeptEntity();
			compDeptEntity.setCompId(compId);
			compDeptEntity.setDeptId(deptId);
//			list.add(compDeptEntity);
			this.save(compDeptEntity);
		}

		// 批量插入
//		this.saveBatch(list);
	}

	/**
	 * 根居公司ID批量删除
	 * @param compIds
	 * @return
	 */
	public boolean deleteBatchByCompIds(Long[] compIds) {
		return this.remove(new QueryWrapper<SysCompDeptEntity>().in("comp_id",compIds));
	}

	public List<Long> findDeptIdList(Long[] compIds) {
		return sysCompDeptMapper.findDeptIdList(compIds);
	}

}
