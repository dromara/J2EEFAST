package com.fast.framework.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.base.entity.Ztree;
import com.fast.framework.sys.entity.SysDeptEntity;

/**
 * 部门管理
 */
public interface SysDeptService extends IService<SysDeptEntity> {

	/**
	 * 查询所有地区
	 */
	List<SysDeptEntity> queryList(Map<String, Object> map);


	List<Ztree> selectDeptTree(Long type);


	/**
	 * 通过公司查询本公司下所有地区信息
	 */
	List<Ztree> selectCompIdDeptTree(Long compId);

	/**
	 * 查询子部门ID列表
	 * 
	 * @param parentId 上级部门ID
	 */
	List<Long> queryDetpIdList(Long parentId);

	/**
	 * 获取子部门ID，用于数据过滤
	 */
	List<Long> getSubDeptIdList(Long deptId);


	boolean checkDeptNameUnique(SysDeptEntity dept);


	SysDeptEntity selectDeptById(Long deptId);


	List<SysDeptEntity> selectDeptList(Map<String, Object> map);


	boolean updateDept(SysDeptEntity dept);


	List<SysDeptEntity> selectByDeptNameId(Map<String, Object> params);

}
