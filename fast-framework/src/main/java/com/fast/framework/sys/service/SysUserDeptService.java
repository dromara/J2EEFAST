package com.fast.framework.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.framework.sys.entity.SysUserDeptEntity;

/**
 * 
 * @Description:用户与公司地区关系
 * @author ZhouHuan 18774995071@163.com
 * @time 2018-12-05 22:58
的
 *
 */
public interface SysUserDeptService extends IService<SysUserDeptEntity> {

	void saveOrUpdate(Long userId, List<Long> deptIdList);

	/**
	 * 根据用户ID，获取地区ID列表
	 */
	List<Long> queryDeptIdList(Long userId);

	List<SysUserDeptEntity> queryListByUserId(Long userId);
	
	/**
	 * 根据地区id获取用户id
	 * @param deptId
	 * @return
	 */
	List<Long> queryUserIdList(Long deptId);

	/**
	 * 根据地区ID数组，批量删除
	 */
	int deleteBatch(Long[] deptIds);

	/**
	 * 根据用户ID数组，批量删除
	 */
	int deleteByUserIdBatch(Long[] userIds);
}
