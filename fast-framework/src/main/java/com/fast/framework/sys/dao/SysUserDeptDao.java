package com.fast.framework.sys.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.sys.entity.SysUserDeptEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @Description:用户与地区关系
 * @author ZhouHuan 18774995071@163.com
 * @time 2018-12-05 22:50
的
 *
 */
public interface SysUserDeptDao extends BaseMapper<SysUserDeptEntity> {
	/**
	 * 根据用户ID，获取地区ID列表
	 */
	List<Long> queryDeptIdList(Long userId);

	List<SysUserDeptEntity> queryListByUserId(@Param("userId") Long userId);

	/**
	 * 根据地区ID数组，批量删除
	 */
	int deleteBatch(Long[] deptIds);

	/**
	 * 根据用户ID数组，批量删除
	 */
	int deleteByUserIdBatch(Long[] userIds);
}
